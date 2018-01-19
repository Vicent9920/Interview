package cn.com.luckytry.interview.ui.diyFile;

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
import org.litepal.crud.DataSupport;

import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterViewMoudle;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.ui.BaseView;
import cn.com.luckytry.interview.ui.parsehtml.WebFactory;
import cn.com.luckytry.interview.ui.widget.ShowTextWebView;
import cn.com.luckytry.interview.util.LUtil;

/**
 * Created by 魏兴 on 2017/9/16.
 */

public class ContentPresenter implements ContentContract.Presenter {

    private ContentContract.View mView;
    private InterViewMoudle mBean = null;
    private String mText;
//    private String url;
    private int playState = -1;
    private SpeechService mSpeechService;
    private Handler mHandler;


    @Override
    public void start() {
        EventBus.getDefault().register(this);
        mHandler = new Handler();
        parpreSource();
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
            mSpeechService.synthesizeToFile(mText);
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
        ClipData mCliplLink = ClipData.newPlainText("Label", mBean.getAdress());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mCliplLink);
    }
    @Override
    public void copyText() {
        ClipboardManager cm = (ClipboardManager) mView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mBean.getText());
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

        share_intent.putExtra(Intent.EXTRA_TEXT, name+mBean.getAdress()+"分享自面试宝典·Android" );

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
        mSpeechService.setNotification(mBean.getAdress(),tag,name);
    }

    @Override
    public boolean addStar() {
        mBean.setStar(!mBean.isStar());
        mBean.save();
        return mBean.isStar();

    }

    @Override
    public void openByBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(mBean.getAdress());
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
    public void getmBean(String id) {
        LUtil.e(id);
        mBean = DataSupport.where("fileName = ?", id).find(InterViewMoudle.class).get(0);

    }

    /**
     * 加载资源
     */
    public void parpreSource() {
        LUtil.e("加载资源"+mBean.getAdress());
        new Thread(new ApiRunnable(mBean.getAdress())).start();
    }

    @Override
    public AppBarStateChangeListener getAppBarStateChangeListener() {
        return new AppBarStateChangeListener();
    }

    @Override
    public void onDestory() {
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 播放状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayStaty(Events<Integer> event) {
        playState = event.content;
    }



    class ApiRunnable implements Runnable{
        private String url;
        public
        ApiRunnable(String url){
            mView.showSource(url);
            this.url = url;
        }
        @Override
        public void run() {


                mText = WebFactory.getWebData(url).getText();
                mBean.setText(mText);
                mBean.setRead(true);
                mBean.saveAsync();

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
