package cn.com.luckytry.interview.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.about.AboutActivity;
import cn.com.luckytry.interview.adapter.MyAdapter;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.star.StarActivity;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;
import cn.com.luckytry.interview.view.DividerItemDecoration;
import cn.com.luckytry.interview.view.SetDialog;
import cn.com.luckytry.interview.view.TitleItemDecoration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainContract.View {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private RecyclerView mRv;
    private View loadingView;
    private View mainView;
    private MyAdapter mAdapter;
    private Toast toast;
    private  boolean move = false;
    private LinearLayoutManager mManager;
    private int mIndex = 0;
    private List<TextView> views = new ArrayList<>();
    private MainPresenter mPresenter;
    private boolean isRecreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
        initViews();
        mPresenter.start();
        LUtil.e(TAG,"onCreate");
    }



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
        }else if(id == R.id.nav_star){
            Intent intent = new Intent(this, StarActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_change) {
            mPresenter.changeTheme();

            isRecreate = true;
            recreate();
        } else if (id == R.id.nav_set) {
            //设置
            SetDialog dialog = new SetDialog(this);
            dialog.builder().show();
        } else if (id == R.id.nav_about) {
            //关于
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrolled(Integer position){
        mPresenter.onScrolled(position);

    }

    /**
     * 点击左侧
     * @param view
     */
    public void doClick(View view){
        String tag = (String) view.getTag();
       mPresenter.getPosition(tag);
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
        TextView tvTitle = (TextView) findViewById(R.id.topbar_title);
        tvTitle.setText(R.string.app_name);
        views.add((TextView) findViewById(R.id.tv1));
        views.add((TextView) findViewById(R.id.tv2));
        views.add((TextView) findViewById(R.id.tv3));
        views.add((TextView) findViewById(R.id.tv4));
        views.add((TextView) findViewById(R.id.tv5));
        views.add((TextView) findViewById(R.id.tv6));
        views.add((TextView) findViewById(R.id.tv7));



        mRv = (RecyclerView) findViewById(R.id.main_rvlist);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
//        List<InterviewBean> data = mPresenter.getData();
//        mAdapter = new MyAdapter(this,R.layout.item_interview,data);
//        mRv.setAdapter(mAdapter);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    move = false;
                    int n = mIndex - mManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRv.getChildCount()) {
                        int top = mRv.getChildAt(n).getTop();
                        mRv.smoothScrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    int n = mIndex - mManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRv.getChildCount()) {
                        int top = mRv.getChildAt(n).getTop();
                        mRv.scrollBy(0, top);
                    }
                }
            }
        });

        loadingView = findViewById(R.id.rl_rl_loading);
        mainView = findViewById(R.id.rl_ll_mian);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading(String info) {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(info);
        loadingView.setVisibility(View.VISIBLE);
        mainView.setVisibility(View.GONE);
    }

    @Override
    public void showResult() {

        List<InterviewBean> data = mPresenter.getData();
        mRv.addItemDecoration(new TitleItemDecoration(this,data));
        mRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MyAdapter(this,R.layout.item_interview,data);
        mRv.setAdapter(mAdapter);
        loadingView.setVisibility(View.GONE);
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updatePart(int current, int last) {
        views.get(current).setEnabled(false);
        if(last != -1){
            views.get(last).setEnabled(true);
        }
    }
    //通过滚动的类型来进行相应的滚动
    @Override
    public void smoothMoveToPosition(int n) {
        mIndex = n;
        mRv.stopScroll();
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRv.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRv.getChildAt(n - firstItem).getTop();
            mRv.smoothScrollBy(0, top);
        } else {
            mRv.smoothScrollToPosition(n);
            move = true;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        LUtil.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LUtil.e(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LUtil.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        LUtil.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LUtil.e(TAG,"onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("recreate",isRecreate);
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean isrecreate = savedInstanceState.getBoolean("recreate");
        if(isrecreate){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                LUtil.e(TAG,"initView");
                int mode = SharedPrefsUtil.getValue(this, Const.THEME_MOUDLE,1,false);
                for (int i = 0; i < views.size(); i++) {
                    //切换主题
                    if(mode == Configuration.UI_MODE_NIGHT_YES) {
                        views.get(i).setBackgroundResource(R.drawable.part_selected_bg_night);

                    } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                        views.get(i).setBackgroundResource(R.drawable.part_selected_bg_24);
                    }
                }

            }
        }
    }

}
