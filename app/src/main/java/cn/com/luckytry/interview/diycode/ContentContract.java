package cn.com.luckytry.interview.diycode;

import cn.com.luckytry.interview.BasePresenter;
import cn.com.luckytry.interview.BaseView;

/**
 * Content
 * Created by 魏兴 on 2017/8/29.
 */

public interface ContentContract {

    interface View extends BaseView<Presenter> {

        void showLoading(String info);

        void showResult();

        void updatePart(int current,int last);
        void smoothMoveToPosition(int n);

    }

    interface Presenter extends BasePresenter {
        void changeTheme();
        void onScrolled(int position);
        void getPosition(String tag);
    }
}
