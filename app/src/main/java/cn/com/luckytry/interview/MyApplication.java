package cn.com.luckytry.interview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;
import cn.com.luckytry.interview.service.ParseDataService;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/24.
 */

public class MyApplication extends MultiDexApplication {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = this;
        int mode = SharedPrefsUtil.getValue(this, Const.THEME_MOUDLE,1,false);
        //切换主题
        if(mode == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Bmob.initialize(this, "28c32139fc01c8e43864b82a76d4c6fb");
        startService(new Intent(this, SpeechService.class));
        startService(new Intent(this, ParseDataService.class));
    }

    public static Context getContext(){
        return mContext;
    }

}
