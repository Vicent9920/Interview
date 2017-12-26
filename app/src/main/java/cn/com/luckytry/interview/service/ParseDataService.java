package cn.com.luckytry.interview.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
import cn.com.luckytry.interview.bean.InterViewMoudle;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.DataBaseHelper;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

public class ParseDataService extends Service {
    private static final String TAG = "ParseDataService";
    public ParseDataService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        parseValue();
    }

    /**
     * 解析数据源
     */
    private void parseValue() {
        try {
            Document doc = Jsoup.connect("http://blog.csdn.net/Vicent_9920/article/details/78797827").get();
            //markdown_views
            Element elementOl = doc.select(".markdown_views").first().select("ol").first();//查找第一个a元素
            ArrayList<Element> elementLis = elementOl.children();
            List<InterViewMoudle> moudles = new ArrayList<>();
            for (int i = 0; i < elementLis.size(); i++) {
                Element item = elementLis.get(i);
                String[] name = item.html().split("<br>");
                ArrayList<Element> itemElementList = item.select("ul").first().children();
                for (int j = 0; j < itemElementList.size(); j++) {
                    Element moudle = itemElementList.get(j);
                    InterViewMoudle interViewMoudle = new InterViewMoudle();
                    interViewMoudle.setFileName(moudle.text());
                    String adress = moudle.child(0).attr("href");
                    interViewMoudle.setAdress(adress);
                    interViewMoudle.setGroup(name[0]);
                    moudles.add(interViewMoudle);
                }
            }
            int size = SharedPrefsUtil.getValue(this, Const.DATABASE_KEY,0,false);
            DataBaseHelper.updateDataBase(size,moudles);
        } catch (IOException e) {
            e.printStackTrace();
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
