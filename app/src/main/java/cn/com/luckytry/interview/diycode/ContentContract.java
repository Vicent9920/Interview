package cn.com.luckytry.interview.diycode;

import cn.com.luckytry.interview.BasePresenter;
import cn.com.luckytry.interview.BaseView;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.view.ShowTextWebView;

/**
 * Content
 * Created by 魏兴 on 2017/8/29.
 */

public interface ContentContract {

    interface View extends BaseView<Presenter> {
        void showResult();
        void onTitle(int state);
        void showSource(String source);




    }

    interface Presenter extends BasePresenter {
        void setSpeechService(SpeechService services);
        void controlPlay();
        void copyLink();
        boolean addStar();
        void openByBrowser ();
        void copyText();
        void stopPlayer();
        void sharLink(String name);
        boolean isPlay();
        boolean isStar();
        boolean isOnLoad();
        void sendNotification(String tag,String name);
        ShowTextWebView.OnChangeListener getOnChangeListener();
        InterviewBean getmBean(String url);
        ContentPresenter.AppBarStateChangeListener getAppBarStateChangeListener();
    }
}
