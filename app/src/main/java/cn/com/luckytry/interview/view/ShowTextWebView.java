package cn.com.luckytry.interview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.util.NetUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/22.
 */

public class ShowTextWebView extends NestedWebView{

    private static final String TAG = "ShowTextWebView";
    private Context context;
    private boolean isLoad = true;
    private OnChangeListener changeListener;

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
                if(changeListener!=null){
                    changeListener.onProgressChanged(newProgress);
                }

            }
        });



        setWebViewClient(new WebViewClient(){


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if(!NetUtil.isConnected(MyApplication.getContext())){
                    Toast.makeText(MyApplication.getContext(),"请检查网络",Toast.LENGTH_LONG).show();
                }
            }
        });

        boolean isShow = SharedPrefsUtil.getValue(getContext(),"BlockNetworkImage",true,false);
        getSettings().setBlockNetworkImage(!isShow);//解决图片不显示

    }

    public void setOnChangeListener(OnChangeListener listener){
        this.changeListener = listener;
    }

    public interface OnChangeListener{
        void onProgressChanged(int newProgress);
        void onReceivedError(WebResourceError error);
    }

}
