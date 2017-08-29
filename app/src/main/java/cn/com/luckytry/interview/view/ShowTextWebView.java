package cn.com.luckytry.interview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/22.
 */

public class ShowTextWebView extends WebView{

    private static final String TAG = "ShowTextWebView";
    private Context context;
    private boolean isLoad = true;
    private onResultCall listener;

    public ShowTextWebView(Context context) {
        super(context);
        init(context);
    }

    public ShowTextWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowTextWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;


        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setDefaultTextEncodingName("UTF -8");
        //启用数据库
        this.getSettings().setDatabaseEnabled(true);

//设置定位的数据库路径
        String dir = this.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        this.getSettings().setGeolocationDatabasePath(dir);

//启用地理定位
        this.getSettings().setGeolocationEnabled(true);

//开启DomStorage缓存
        this.getSettings().setDomStorageEnabled(true);

        //获取 html
        this.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

//配置权限
        setWebChromeClient(new WebChromeClient() {
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
                Events<Integer> events = new Events<Integer>();
                events.setContent(newProgress);
                EventBus.getDefault().post(events);
            }
        });



        setWebViewClient(new WebViewClient(){
            // 网页加载结束
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                if(isLoad){

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                                            + "document.getElementsByTagName('body')[0].innerHTML+'</head>');");

                                }
                            });
                        }
                    },1000);

                    isLoad = false;
                }

            }
        });

        boolean isShow = SharedPrefsUtil.getValue(getContext(),"BlockNetworkImage",true,false);
        getSettings().setBlockNetworkImage(!isShow);//解决图片不显示

    }
    public void setResultCall(onResultCall call){
        this.listener = call;
    }

    private void loadValue( final String html){
        try {

//        if(listener!=null){
//            listener.load(source);
//        }

            post(new Runnable() {
                @Override
                public void run() {

                    String source = Const.getData(html);
                    loadDataWithBaseURL(null, source, "text/html", "utf-8",null);
                    getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    if(listener!=null){
                        listener.load(html);
                    }
                }
            });

        } catch (Exception e) {
            LUtil.e(TAG,"loadValueException",e);
        }
    }

    public void isShowSource(){
        isLoad = false;
    }

    private class InJavaScriptLocalObj {
        /**
         * 获取要解析 WebView 加载对应的 Html 文本
         *
         * @param html WebView 加载对应的 Html 文本
         */
        @android.webkit.JavascriptInterface
        public void showSource(String html) {

            try {
                Document doc = Jsoup.parse(html);
                loadValue(doc.getElementsByTag("article").html());
            } catch (Exception e) {
                LUtil.e(TAG,"showSourceException",e);
            }

        }
    }

    public interface onResultCall{
        void load(String html);
    }


}
