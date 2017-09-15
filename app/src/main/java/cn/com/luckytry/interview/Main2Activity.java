package cn.com.luckytry.interview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.com.luckytry.interview.main.MainActivity;

public class Main2Activity extends AppCompatActivity {

    private WebView webView;
    boolean isNeedExe = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:function myFunction(){\n" +
                        "var $jquery = jQuery.noConflict();\n" +
                        "var content=$(\"article\").get(0);\n" +
                        "$jquery('body').empty();\n" +
                        "content.css({\n" +
                        "background:\"#fff\",\n" +
                        "position:\"absolute\",\n" +
                        "top:\"0\",left:\"0\",\n" +
                        "});\n" +
                        "$jquery('body').append(content);\n" +
                        "$jquery(\"div\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"a\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h1\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h2\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h3\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h4\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h5\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"h6\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"img\").removeAttr(\"class\").removeAttr(\"style\").removeAttr(\"id\");\n" +
                        "$jquery(\"img\").css({width: \"100%\",height:\"100%\",objecFit:\"cover\"});\n" +
                        "$jquery(\"h1\").css({paddingBottom: \"0.3em\",fontSize:\"2em\",borderBottom:\"1px solid #eaecef\"});\n" +
                        "$jquery(\"h2\").css({paddingBottom: \"0.3em\",fontSize:\"1.5em\",borderBottom:\"1px solid #eaecef\"});\n" +
                        "$jquery(\"h3\").css({fontSize:\"1.25em\"});\n" +
                        "$jquery(\"h4\").css({fontSize:\"1em\"});\n" +
                        "$jquery(\"h5\").css({fontSize:\"0.875em\"});\n" +
                        "$jquery(\"h6\").css({fontSize:\"0.85em\"});\n}");
                view.loadUrl("javascript:myFunction()");
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 95 && isNeedExe) {
                    isNeedExe = !isNeedExe;
                    String jsContent = assetFile2Str(view.getContext(), "jquery-3.2.1.min.js");
                    view.loadUrl("javascript:" + jsContent);

                }

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://github.com/francistao/LearningNotes/blob/master/Part1/Android/Android%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96.md");

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
}
