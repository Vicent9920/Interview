package cn.com.luckytry.interview;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import org.litepal.LitePal;

import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/24.
 */

public class MyApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = this;
        int mode = SharedPrefsUtil.getValue(this, Const.THEME_MOUDLE,1,false);
        //切换主题
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    public static Context getContext(){
        return mContext;
    }

}
