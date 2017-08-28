package cn.com.luckytry.interview.diycode;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.about.AboutActivity;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.diycode.view.TitleItemDecoration;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.view.DividerItemDecoration;
import cn.com.luckytry.interview.view.SetDialog;

public class TestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView mRv;
    private CommonAdapter<InterviewBean> mAdapter;
    private List<InterviewBean> data;
    private Toast toast;
    private  boolean move = false;
    private LinearLayoutManager mManager;
    private int mIndex = 0;
    private int lastIndex = -1;
    private HashMap<String,Integer> map = new HashMap<>();
    private HashMap<String,String> mapPart = new HashMap<>();
    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initData();
        initView();


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        for (int i = 0; i < 7; i++) {
            map.put("第"+(i+1)+"部分",i);
            mapPart.put(""+i,"第"+(i+1)+"部分");
        }

    }


    private void initView() {
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
        data = DataSupport.findAll(InterviewBean.class);
        mAdapter = new CommonAdapter<InterviewBean>(this,R.layout.item_interview,data) {
            @Override
            protected void convert(ViewHolder holder, final InterviewBean interviewBean, int position) {

                holder.setText(R.id.tvInterview,interviewBean.getName());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(interviewBean.isCanLoad()){
                            Intent intent = new Intent(TestActivity.this, ContentActivity.class);
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
        };
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new TitleItemDecoration(this,data));
        mRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
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
            int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            //切换主题
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // blah blah
            }

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
        String part = data.get(position).getPart();
        int current = map.get(part);
        if(current!=lastIndex){
            updatePart(current);
        }

        LUtil.e("右侧position:"+part);

    }

    private void updatePart(int current) {
        views.get(current).setEnabled(false);
        if(lastIndex != -1){
            views.get(lastIndex).setEnabled(true);
        }
        lastIndex = current;
    }

    //通过滚动的类型来进行相应的滚动

    private void smoothMoveToPosition(int n) {
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

    /**
     * 左侧联动右侧
     * @param tag
     */
    public void setData(String tag) {
        String part = mapPart.get(tag);
        List<InterviewBean> parts = DataSupport.where("part = ?", part).find(InterviewBean.class);
        InterviewBean bean = parts.get(0);
        int n = getIndex(bean);
        LUtil.e("右侧显示位置:"+n);
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    /**
     * 获取位置
     * @param bean
     * @return
     */
    private int getIndex(InterviewBean bean) {
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals(bean.getName()))
                return i;
        }
        return -1;
    }

    /**
     * 点击左侧
     * @param view
     */
    public void doClick(View view){
        String tag = (String) view.getTag();
        setData(tag);
        LUtil.e("doClick:"+tag);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
