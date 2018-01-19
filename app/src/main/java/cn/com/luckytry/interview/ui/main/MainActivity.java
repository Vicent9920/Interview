package cn.com.luckytry.interview.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.List;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterViewMoudle;
import cn.com.luckytry.interview.ui.main.fragment.FragmentController;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainContract.View {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private FragmentController mController;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mController = FragmentController.getInstance(this, R.id.flayout_content, true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mController.showFragment(0,"Android");
        tvTitle.setText("Android");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_main_item0:
                mController.showFragment(0,"Android");
                tvTitle.setText("Android");
                //Android
                break;
            case R.id.nav_main_item1:
                //设计模式
                mController.showFragment(0,"设计模式");
                tvTitle.setText("设计模式");
                break;
            case R.id.nav_main_item2:
                //java 基础
                mController.showFragment(0,"java 基础");
                tvTitle.setText("java 基础");
                break;
            case R.id.nav_main_item3:
                //java 并发
                mController.showFragment(0,"java 并发");
                tvTitle.setText("java 并发");
                break;
            case R.id.nav_main_item4:
                //数据结构
                mController.showFragment(0,"数据结构");
                tvTitle.setText("数据结构");
                break;
            case R.id.nav_main_item5:
                //算法
                mController.showFragment(0,"算法");
                tvTitle.setText("算法");
                break;
            case R.id.nav_main_item6:
                //网络
                mController.showFragment(0,"网络");
                tvTitle.setText("网络");
                break;
            case R.id.nav_main_item7:
                //读书笔记
                mController.showFragment(0,"读书笔记");
                tvTitle.setText("读书笔记");
                break;
            case R.id.nav_main_item8:
                //面试经验
                mController.showFragment(0,"面试经验");
                tvTitle.setText("面试经验");
                break;
//            case R.id.nav_main:
//                //首页
////            mRv.removeItemDecoration(mTitleItemDecoration);
//                break;
            case R.id.nav_star:
                //收藏
                mController.showFragment(0,"star");
                tvTitle.setText("收藏");
//                Intent intent = new Intent(this, StarActivity.class);
//                startActivity(intent);
                break;
            case R.id.nav_change:
                //改变主题
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if(mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPrefsUtil.putValue(MyApplication.getContext(), Const.THEME_MOUDLE,1);

                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPrefsUtil.putValue(MyApplication.getContext(),Const.THEME_MOUDLE,2);

                }
                mController.removeFragments();
                recreate();
                break;

            case R.id.nav_about:
                //关于
                mController.showFragment(1,"star");
//                startActivity(new Intent(this, AboutActivity.class));
                tvTitle.setText("关于");
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvTitle = (TextView) findViewById(R.id.topbar_title);
        tvTitle.setText("Andoid");

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading(String info) {
    }

    @Override
    public void showErrorInfo(String info) {
    }

    @Override
    public void showResult() {

    }

    @Override
    public void updatePart(int current, int last) {
    }
    //通过滚动的类型来进行相应的滚动
    @Override
    public void smoothMoveToPosition(int n) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(InterViewMoudle moudle) {
        List<InterViewMoudle> Data = DataSupport.findAll(InterViewMoudle.class);
        LUtil.e("数据长度："+Data.size());
        mController.showFragment(0,"Android");
        tvTitle.setText("Android");
    };

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.onDestroy();
    }



}
