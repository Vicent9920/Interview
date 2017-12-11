package cn.com.luckytry.interview.ui.diyFile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.webkit.WebResourceError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterViewInfo;
import cn.com.luckytry.interview.bean.InterViewUser;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.ui.BaseView;
import cn.com.luckytry.interview.ui.parsehtml.WebFactory;
import cn.com.luckytry.interview.ui.widget.ShowTextWebView;
import cn.com.luckytry.interview.util.LUtil;

import static cn.bmob.v3.BmobUser.getCurrentUser;

/**
 * Created by 魏兴 on 2017/9/16.
 */

public class ContentPresenter implements ContentContract.Presenter {

    private ContentContract.View mView;
    private InterViewInfo mBean = null;
    private String mText;
    private String url;
    private int playState = -1;
    private SpeechService mSpeechService;
    private Handler mHandler;
    InterViewUser user = BmobUser.getCurrentUser(InterViewUser.class);

    @Override
    public void start() {
        EventBus.getDefault().register(this);
        mHandler = new Handler();
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
            mSpeechService.synthesizeToFile(mText,mBean.getObjectId());
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
        ClipData mClipData = ClipData.newPlainText("Label", mBean.getFile_Content());
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
        List<String> starData = user.getStars();
        if(starData.size() == 0){
            return false;
        }

        for (int i = 0; i < starData.size(); i++) {
            if(starData.get(i).equals(mBean.getObjectId())){
                return true;
            }
        }
       return false;
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
        ArrayList<String> starData = user.getStars();

        final boolean state = isStar();
        if(state){
            for (int i = 0; i < starData.size(); i++) {
                if(starData.get(i).equals(mBean.getObjectId())){
                    starData.remove(i);
                    break;
                }
            }
        }else{
            starData.add(mBean.getObjectId());
        }
        user.setReaded(starData);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                LUtil.e("修改收藏结束："+!state);
            }
        });

        return state;

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
    public InterViewInfo getmBean(String id) {
        this.url = id;
//        List<InterviewBean> beans = DataSupport.where("adress = ?", url).find(InterviewBean.class);
//        if(beans.size()>0){
//
//             mBean = beans.get(0);
//
//            if(mBean.isValidContent()){
//                String source = Const.getData(mView.getContext(),mBean.getContent());
//                mView.showSource(source);
//
//            }else{
//

//            }
//        }

        BmobQuery<InterViewInfo> query = new BmobQuery<InterViewInfo>();
        query.getObject(id, new QueryListener<InterViewInfo>() {

            @Override
            public void done(InterViewInfo object, BmobException e) {
                if(e==null){
                    mBean = object;
                    parpreSource();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    //object.getCreatedAt();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
        return mBean;
    }

    /**
     * 加载资源
     */
    public void parpreSource() {

        new Thread(new ApiRunnable(mBean.getFile_Link())).start();
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
//                Document doc = Jsoup.connect(url).get();
//                final String html ;
//
//                if(url.startsWith("http://www.jianshu.com")){
////                    html = doc.getElementsByClass("content").html();
//                    html = doc.select("div.show-content").first().html();
//                    mText = doc.select("div.show-content").first().text();
//                }else if(url.startsWith("https://github.com/")){
//                    html = doc.getElementsByTag("article").html();
//                    mText = doc.getElementsByTag("article").text();
//                }else if(url.startsWith("http://p.codekk.com/blogs")){
//                    html = doc.select("div.hero-unit").first().html();
//                    mText = doc.select("div.hero-unit").first().text();
//                }else{
//                    html = "";
//                }

                mText = WebFactory.getWebData(url).getText();
//               mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        String source = Const.getData(mView.getContext(),html);
//                        mView.showSource(source);
//                    }
//                });
                mBean.setFile_Content(mText);
                mBean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                InterViewUser user = getCurrentUser(InterViewUser.class);
                user.getReaded().add(mBean.getObjectId());
                LUtil.e("已读数量："+user.getReaded().size());
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            LUtil.e("完成添加");
                        }else{
                            LUtil.e(e.getMessage());
                        }
                    }
                });

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
