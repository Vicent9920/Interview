package cn.com.luckytry.interview.ui.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private WebView webView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        webView = (WebView) view.findViewById(R.id.about_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.startsWith(getResources().getString(R.string.about_email))){
                    sendMailByIntent();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int mode = SharedPrefsUtil.getValue(MyApplication.getContext(), Const.THEME_MOUDLE,1,false);
                if(mode==2){
                    webView.loadUrl("javascript:night()");
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/InterView/about.html");
    }

    public int sendMailByIntent() {
        String[] reciver = new String[] { "weixing9920@163.com" };
        String[] mySbuject = new String[] { "InterViewBug" };
        String myCc = "cc";
        String mybody = "异常内容：";
        Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
        myIntent.setType("plain/text");
        myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
        myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);
        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);
        startActivity(Intent.createChooser(myIntent, "mail test"));

        return 1;

    }

}
