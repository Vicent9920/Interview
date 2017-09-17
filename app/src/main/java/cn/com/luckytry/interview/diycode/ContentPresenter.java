package cn.com.luckytry.interview.diycode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.webkit.WebResourceError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.luckytry.interview.BaseView;
import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.view.ShowTextWebView;

/**
 * Created by 魏兴 on 2017/9/16.
 */

public class ContentPresenter implements ContentContract.Presenter {

    private ContentContract.View mView;
    private InterviewBean mBean = null;
    private String mText;
    private String url;
    private int playState = -1;
    private SpeechService mSpeechService;
    private  ExecutorService mPool = Executors.newSingleThreadExecutor();

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void setView(BaseView baseView) {
        this.mView = (ContentContract.View) baseView;
    }


    @Override
    public void setSpeechService(SpeechService services) {
        mSpeechService = services;
    }

    @Override
    public void controlPlay() {
        if(playState == -1){//停止状态
            mSpeechService.synthesizeToFile(mText,mBean.getId());
            playState = 1;
        }else if(playState == 1){//播放状态
            mSpeechService.pausePayler();
            playState++;
        }else if(playState == 2){//暂停状态
            mSpeechService.resumePlayer();
            playState = 1;
        }
    }

    @Override
    public void copyLink() {
        ClipboardManager cm = (ClipboardManager) mView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mCliplLink = ClipData.newPlainText("Label", url);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mCliplLink);
    }
    @Override
    public void copyText() {
        ClipboardManager cm = (ClipboardManager) mView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mBean.getContent());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void stopPlayer() {
        mSpeechService.stopPlayer();
    }

    @Override
    public void sharLink(String name) {
        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "面试宝典");

        share_intent.putExtra(Intent.EXTRA_TEXT, name+url+"分享自面试宝典·Android" );

        share_intent = Intent.createChooser(share_intent, "分享");

        mView.getContext().startActivity(share_intent);
    }

    @Override
    public boolean isPlay() {
        return mSpeechService.isPlay();
    }

    @Override
    public boolean isStar() {
        return mBean.isStar();
    }

    @Override
    public boolean isOnLoad() {
        return mText == null;
    }

    @Override
    public void sendNotification(String tag, String name) {
        mSpeechService.setNotification(url,tag,name);
    }

    @Override
    public boolean addStar() {
        boolean state = mBean.isStar();
        mBean.setStar(!state);
        mBean.save();


        if(state){
            return true;
        }
        return false;

    }

    @Override
    public void openByBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        mView.getContext().startActivity(intent);
    }





    @Override
    public ShowTextWebView.OnChangeListener getOnChangeListener() {
        return new ShowTextWebView.OnChangeListener() {
            @Override
            public void onProgressChanged(int newProgress) {

            }

            @Override
            public void onReceivedError(WebResourceError error) {

            }
        };
    }



    @Override
    public InterviewBean getmBean(String url) {
        this.url = url;
        List<InterviewBean> beans = DataSupport.where("adress = ?", url).find(InterviewBean.class);
        if(beans.size()>0){
             mBean = beans.get(0);
            if(mBean.isValidContent()){
                String source = Const.getData(mView.getContext(),mBean.getContent());
                mView.showSource(source);

            }else{

            mPool.execute(new ApiRunnable(url,new Handler()));
            }
        }
        return mBean;
    }

    @Override
    public AppBarStateChangeListener getAppBarStateChangeListener() {
        return new AppBarStateChangeListener();
    }

    @Override
    public void onDestory() {
        EventBus.getDefault().unregister(this);
        mPool.shutdown();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayStaty(Events<Integer> event) {
        playState = event.content;
    }

    class ApiRunnable implements Runnable{
        private String url;
        private Handler mHandler;
        public ApiRunnable(String url,Handler handler){
            this.url = url;
            this.mHandler = handler;
        }
        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect(url).get();
                final String html ;

                boolean isJianShu = url.startsWith("http://www.jianshu.com");
                if(isJianShu){
//                    html = doc.getElementsByClass("content").html();
                    html = doc.select("div.content").first().html();
                    mText = doc.select("div.content").first().text();
                }else{
                    html = doc.getElementsByTag("article").html();
                    mText = doc.getElementsByTag("article").text();
                }
               mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String source = Const.getData(mView.getContext(),html);
                        mView.showSource(source);
                    }
                });
                mBean.setContent(html);
                mBean.setText(mText);
                mBean.saveAsync().listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        mBean.setValidContent(true);
                        mBean.saveAsync();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {


        public static final int EXPANDED = 0;
        public static final int COLLAPSED = 1;
        public static final int IDLE = 2;



        private int mCurrentState = IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != EXPANDED) {
                    mView.onTitle(EXPANDED);
                }
                mCurrentState = EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != COLLAPSED) {
                    mView.onTitle(COLLAPSED);
                }
                mCurrentState = COLLAPSED;
            } else {
                if (mCurrentState != IDLE) {
                    mView.onTitle(IDLE);
                }
                mCurrentState = IDLE;
            }
        }


    }
}
