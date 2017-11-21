package cn.com.luckytry.interview.ui;

import android.content.Context;

/**
 * MVP View基类
 * Created by 魏兴 on 2017/8/29.
 */

public interface BaseView<T> {
    //初始化View
    void initViews();
    Context getContext();
}
