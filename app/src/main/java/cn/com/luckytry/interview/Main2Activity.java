package cn.com.luckytry.interview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.com.luckytry.interview.main.MainActivity;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.NetUtil;

public class Main2Activity extends AppCompatActivity {

    private WebView webView;
    boolean isNeedExe = true;
    private Thread mThread;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LUtil.e("ThreadId:"+Thread.currentThread().getId());
            loadValue((String) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       webView = (WebView) findViewById(R.id.web_view);
        init();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("https://github.com/francistao/LearningNotes/blob/master/Part1/Android/%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8E%A7%E4%BB%B6.md").get();
                    String html = doc.getElementsByTag("article").html();
                    Message msg = mHandler.obtainMessage();
                    msg.obj = html;
                    LUtil.e("ThreadId:"+Thread.currentThread().getId());
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        mThread = new MyThread();
        mThread.start();
    }

    private void loadValue( final String html){
        String source = Const.getData(this,html);
        LUtil.e("loadValue:"+source);
        webView.loadDataWithBaseURL(null, source, "text/html", "utf-8",null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

    }

    private void init(){

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        //启用数据库
        webView.getSettings().setDatabaseEnabled(true);

//设置定位的数据库路径
        String dir = webView.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setGeolocationDatabasePath(dir);

//启用地理定位
        webView.getSettings().setGeolocationEnabled(true);

//开启DomStorage缓存
        webView.getSettings().setDomStorageEnabled(true);

        //获取 html
//        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

//配置权限
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);


            }
        });



        webView.setWebViewClient(new WebViewClient(){
            // 网页加载结束
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                LUtil.e("onPageFinished:"+System.currentTimeMillis());

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if(!NetUtil.isConnected(MyApplication.getContext())){
                    Toast.makeText(MyApplication.getContext(),"请检查网络",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });


        webView.getSettings().setBlockNetworkImage(true);//解决图片不显示

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mThread.isAlive()){
            mThread.stop();
        }
    }

    public static String assetFile2Str(Context c, String urlStr){
        InputStream in = null;
        try{
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
    private void sendIntent() {
        /**
         * 该方法虽然被抛弃过时，但是通用！
         */
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,
                        0, new Intent(this, MainActivity.class), 0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.play_notification);
        Notification notification = new Notification(R.mipmap.logo,
                "歌曲正在播放", System.currentTimeMillis());
        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;
        //标记位，设置通知栏一直存在
        notification.flags =Notification.FLAG_ONGOING_EVENT;

        Intent intent = new Intent("PlayService");
        intent.putExtra("BUTTON_NOTI", 1);
        PendingIntent preIntent = PendingIntent.getBroadcast(
                this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_pre, preIntent);

        intent.putExtra("BUTTON_NOTI", 2);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(
                this,
                2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_exit, pauseIntent);



        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //通知栏更新
        notificationManager.notify(5, notification);
    }


    public PendingIntent getDefalutIntent(int flags){
         PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
         return pendingIntent;
         }

         class MyThread extends Thread{
             @Override
             public void run() {
                 super.run();
                 int i = 0;
                 while (true){
                     i++;
                     LUtil.e("当前线程"+i);
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         LUtil.e("睡眠失败",e);
                         e.printStackTrace();
                     }
                 }
             }
         }
}
