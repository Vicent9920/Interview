package cn.com.luckytry.interview.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.com.luckytry.interview.bean.InterViewInfo;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.LUtil;

public class ParseDataService extends Service {
    private static final String TAG = "ParseDataService";
    public ParseDataService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        List<InterviewBean> data = DataSupport.findAll(InterviewBean.class);
        if(data.size() == 0){
            String value = getDataFromAssetsFile("InterView/main.html");
            parseValue(value);
        }
    }

    /**
     * 解析数据源
     * @param value
     */
    private void parseValue(String value) {
        Document doc = Jsoup.parse(value);
        Elements parts = doc.getElementsByClass("part");
        for (int i = 0; i < parts.size(); i++) {
            Element part = parts.get(i);
            ArrayList<Element> tag = part.select(".packge");
            for (Element element:tag) {
                Element tagP = element.getElementsByTag("p").first();
                if(tagP==null){
                    String href = element.getElementsByTag("a").first().attr("href");
                    InterviewBean interviewBean = new InterviewBean();
                    interviewBean.setPart("第"+(i+1)+"部分");
                    interviewBean.setTag("第"+(i+1)+"部分");
                    interviewBean.setName(element.text());
                    interviewBean.setAdress(href);
                    interviewBean.setCanLoad(true);
                    interviewBean.save();
                    LUtil.e("bean:"+interviewBean.toGson());

                    break;
                }
                Elements beans = element.getElementsByTag("ul").first().getElementsByTag("li");
                for (Element bean:beans) {
                    if(bean.getElementsByTag("ul").first()==null){
                        String href = "";
                        if(bean.getElementsByTag("a").size() != 0){
                            href = bean.getElementsByTag("a").first().attr("href");
                        }
                        InterviewBean interviewBean = new InterviewBean();
                        interviewBean.setPart("第"+(i+1)+"部分");
                        interviewBean.setTag(tagP.text());
                        interviewBean.setName(bean.text());
                        interviewBean.setAdress(href);
                        interviewBean.setCanLoad(href.length() != 0);
                        interviewBean.save();
                        LUtil.e("bean:"+interviewBean.toGson());

                    }else{
                        String beantag = bean.text();
                        if(beantag.contains(" ")){
                            String[] title = beantag.split(" ");
                            beantag = title[0];
                        }
                        ArrayList<Element> beanlist = bean.getElementsByTag("ul").first().getElementsByTag("li");
                        if(beanlist.size()!=0){
                            for (Element beanData:beanlist) {
                                String href = "";
                                if(beanData.getElementsByTag("a").size() != 0){
                                    href = beanData.getElementsByTag("a").first().attr("href");
                                }
                                InterviewBean interviewBean = new InterviewBean();
                                interviewBean.setPart("第"+(i+1)+"部分");
                                interviewBean.setTag(tagP.text());
                                interviewBean.setName(beantag+"  "+beanData.text());
                                interviewBean.setAdress(href);
                                interviewBean.setCanLoad(href.length() != 0);
                                interviewBean.save();
                                LUtil.e("bean:"+interviewBean.toGson());

                            }
                        }else{
                            LUtil.e("err");
                        }
                    }
                }
            }
        }
        List<InterviewBean> data = DataSupport.findAll(InterviewBean.class);
        List<BmobObject> infos = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            InterviewBean bean = data.get(i);
            if(bean.isCanLoad()){
                InterViewInfo info = new InterViewInfo();
                info.setPart(bean.getPart());
                info.setType(bean.getTag());
                info.setName(bean.getName());
                info.setRead(false);
                info.setFile_Link(bean.getAdress());
                info.setFile_Type(1);
                info.setStar(false);
                infos.add(info);
            }

        }
        LUtil.e(TAG,"有效数据："+infos.size());
        for (int i = 0; i < (Math.ceil(infos.size()/50f)); i++) {
            List<BmobObject> values;
                if(i==(Math.ceil(infos.size()/50f))-1){
                    values = infos.subList(i*50,infos.size());
                }else{
                    values = infos.subList(i*50,i*50+50);
                }
            sumbitData(values);
        }

    }

    private void sumbitData(List<BmobObject> infos) {
        new BmobBatch().insertBatch(infos).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            LUtil.e(TAG,"第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
                        }else{
                            LUtil.e(TAG,"第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    LUtil.e(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 从Assets中读取文件
     * @param fileName
     * @return
     */
    private String getDataFromAssetsFile(String fileName)
    {

        AssetManager am = getResources().getAssets();
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            InputStream is = am.open(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                    }
            is.close();
        }
        catch (IOException e)
        {
            LUtil.e("getImageFromAssetsFile",e);
            return e.getMessage();
        }

        return sb.toString();

    }
}
