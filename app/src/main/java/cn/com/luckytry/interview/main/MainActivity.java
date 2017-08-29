package cn.com.luckytry.interview.main;

import android.content.Context;
import android.content.Intent;
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

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.about.AboutActivity;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.diycode.ContentActivity;
import cn.com.luckytry.interview.view.DividerItemDecoration;
import cn.com.luckytry.interview.view.SetDialog;
import cn.com.luckytry.interview.view.TitleItemDecoration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainContract.View {

    private DrawerLayout drawer;
    private RecyclerView mRv;
    private View loadingView;
    private View mainView;
    private MyAdapter mAdapter;
    private Toast toast;
    private  boolean move = false;
    private LinearLayoutManager mManager;
    private int mIndex = 0;
    private List<View> views = new ArrayList<>();
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
        initViews();
        mPresenter.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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
        } else if (id == R.id.nav_change) {
            mPresenter.changeTheme();


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
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

        views.add(findViewById(R.id.tv1));
        views.add(findViewById(R.id.tv2));
        views.add(findViewById(R.id.tv3));
        views.add(findViewById(R.id.tv4));
        views.add(findViewById(R.id.tv5));
        views.add(findViewById(R.id.tv6));
        views.add(findViewById(R.id.tv7));
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

    class MyAdapter extends CommonAdapter<InterviewBean>{

        private List<InterviewBean> data;
        public MyAdapter(Context context, int layoutId, List<InterviewBean> datas) {
            super(context, layoutId, datas);
            this.data = datas;
        }

        public void setData(List<InterviewBean> data){
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        protected void convert(ViewHolder holder, final InterviewBean interviewBean, int position) {
            holder.setText(R.id.tvInterview,interviewBean.getName());
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interviewBean.isCanLoad()){
                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                        intent.putExtra("url",interviewBean.getAdress());
                        intent.putExtra("name",interviewBean.getName());
                        intent.putExtra("tag",interviewBean.getTag());
                        startActivity(intent);
                    }else{
                        toast.setText(interviewBean.getName()+" 作者暂未开发！");
                        toast.show();
                    }
                }
            });
        }
    }
}