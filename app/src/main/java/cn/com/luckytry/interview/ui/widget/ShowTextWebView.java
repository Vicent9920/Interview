package cn.com.luckytry.interview.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebView;

import cn.com.luckytry.interview.jsbridge.BridgeWebView;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/22.
 */

public class ShowTextWebView extends BridgeWebView{

    private static final String TAG = "ShowTextWebView";


    public ShowTextWebView(Context context) {
        this(context,null);

    }

    public ShowTextWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ShowTextWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setDefaultTextEncodingName("UTF -8");
        this.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

        //配置权限
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(changeListener!=null){
                    changeListener.onProgressChanged(newProgress);
                }
                if(newProgress>95&& isError){
                    isError = !isError;
                    view.loadUrl("javascript:loaderror("+code+""+","+info+");");
                }
            }
        });

        boolean isShow = SharedPrefsUtil.getValue(getContext(),"BlockNetworkImage",true,false);
        getSettings().setBlockNetworkImage(!isShow);//解决图片不显示
        this.loadUrl("file:///android_asset/InterView/index.html");
    }

    public void loadingUrl(String url){
        baseUrl = url;
    }



    public void setOnChangeListener(OnChangeListener listener){
        this.changeListener = listener;
    }

    public interface OnChangeListener{
        void onProgressChanged(int newProgress);
        void onReceivedError(WebResourceError error);
    }

}
