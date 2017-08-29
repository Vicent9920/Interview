package cn.com.luckytry.interview;

/**
 * MVP Presenter基类
 * Created by 魏兴 on 2017/8/29.
 */

public interface BasePresenter {


    //初始化
    void start();
    void setView(BaseView baseView);
    void onDestory();

}
