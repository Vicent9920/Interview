package cn.com.luckytry.interview.main;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

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
import java.util.HashMap;
import java.util.List;

import cn.com.luckytry.interview.BaseView;
import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/29.
 */

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private List<InterviewBean> data = new ArrayList<>();
    private HashMap<String,Integer> map = new HashMap<>();
    private HashMap<String,String> mapPart = new HashMap<>();
    private int lastIndex = -1;
    @Override
    public void start() {
        data = DataSupport.findAll(InterviewBean.class);
        if(data.size()==0){
            mView.showLoading(mView.getContext().getResources().getString(R.string.main_loading));
            parseValue(getDataFromAssetsFile(mView.getContext().getString(R.string.main_file)));
            data = DataSupport.findAll(InterviewBean.class);
            if(data.size()>0){
                LUtil.e("size:"+data.size());
                mView.showResult();

            }else{
                mView.showLoading(mView.getContext().getString(R.string.main_loaderror));
            }
        }else{
            mView.showResult();
        }
        for (int i = 0; i < 7; i++) {
            map.put("第"+(i+1)+"部分",i);
            mapPart.put(""+i,"第"+(i+1)+"部分");
        }
    }

    @Override
    public void setView(BaseView baseView) {
        this.mView = (MainContract.View) baseView;
    }
    /**
     * 从Assets中读取文件
     * @param fileName
     * @return
     */
    private String getDataFromAssetsFile(String fileName)
    {

        AssetManager am = mView.getContext().getResources().getAssets();
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

                if(tagP==null ){
                    String href = element.getElementsByTag("a").first().attr("href");
//                    if((href.startsWith("http://www.jianshu.com/") || href.startsWith("https://github.com/"))){
//
//                    }
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
//                        if((href.startsWith("http://www.jianshu.com/") || href.startsWith("https://github.com/"))){
//
//                        }
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
//                                if((href.startsWith("http://www.jianshu.com/") || href.startsWith("https://github.com/"))){
//
//                                }
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
                    }
                }
            }
        }
    }

    public List<InterviewBean> getData(){
        if(data == null)
            data = new ArrayList<>();
        return data;
    }

    @Override
    public void onDestory() {
        mView = null;
    }


    @Override
    public void changeTheme() {
        int mode = mView.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        int mode = SharedPrefsUtil.getValue(MyApplication.getContext(), Const.THEME_MOUDLE,1,false);
        //切换主题
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPrefsUtil.putValue(MyApplication.getContext(),Const.THEME_MOUDLE,1);
            LUtil.e("切换主题1");
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPrefsUtil.putValue(MyApplication.getContext(),Const.THEME_MOUDLE,2);
            LUtil.e("切换主题2");
        }
    }

    @Override
    public void onScrolled(int position) {
        String part = data.get(position).getPart();
        int current = map.get(part);
        if(current!=lastIndex){
            mView.updatePart(current,lastIndex);
            lastIndex = current;
        }

    }

    @Override
    public void getPosition(String tag) {
        String part = mapPart.get(tag);
        List<InterviewBean> parts = DataSupport.where("part = ?", part).find(InterviewBean.class);
        InterviewBean bean = parts.get(0);
        int n = getIndex(bean);
        mView.smoothMoveToPosition(n);
    }

    /**
     * 获取位置
     * @param bean
     * @return
     */
    private int getIndex(InterviewBean bean) {
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals(bean.getName()))
                return i;
        }
        return -1;
    }
}
