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

import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.LUtil;

public class ParseDataService extends Service {
    public ParseDataService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        List<InterviewBean> data = DataSupport.findAll(InterviewBean.class);
        if(data.size() == 0){
            String value = getDataFromAssetsFile("main.html");
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
                    if(href.startsWith("http://www.jianshu.com")||href.startsWith("https://github.com")){
                        InterviewBean interviewBean = new InterviewBean();
                        interviewBean.setPart("第"+(i+1)+"部分");
                        interviewBean.setTag("第"+(i+1)+"部分");
                        interviewBean.setName(element.text());
                        interviewBean.setAdress(href);
                        interviewBean.setCanLoad(true);
                        interviewBean.save();
                        LUtil.e("bean:"+interviewBean.toGson());
                    }

                    break;
                }
                Elements beans = element.getElementsByTag("ul").first().getElementsByTag("li");
                for (Element bean:beans) {
                    if(bean.getElementsByTag("ul").first()==null){
                        String href = "";
                        if(bean.getElementsByTag("a").size() != 0){
                            href = bean.getElementsByTag("a").first().attr("href");
                        }
                        if(href.startsWith("http://www.jianshu.com")||href.startsWith("https://github.com")){
                            InterviewBean interviewBean = new InterviewBean();
                            interviewBean.setPart("第"+(i+1)+"部分");
                            interviewBean.setTag(tagP.text());
                            interviewBean.setName(bean.text());
                            interviewBean.setAdress(href);
                            interviewBean.setCanLoad(href.length() != 0);
                            interviewBean.save();
                            LUtil.e("bean:"+interviewBean.toGson());
                        }

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
                                if(href.startsWith("http://www.jianshu.com")||href.startsWith("https://github.com")){
                                    InterviewBean interviewBean = new InterviewBean();
                                    interviewBean.setPart("第"+(i+1)+"部分");
                                    interviewBean.setTag(tagP.text());
                                    interviewBean.setName(beantag+"  "+beanData.text());
                                    interviewBean.setAdress(href);
                                    interviewBean.setCanLoad(href.length() != 0);
                                    interviewBean.save();
                                    LUtil.e("bean:"+interviewBean.toGson());
                                }

                            }
                        }else{
                            LUtil.e("err");
                        }
                    }
                }
            }
        }
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
