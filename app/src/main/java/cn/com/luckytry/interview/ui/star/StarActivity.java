package cn.com.luckytry.interview.ui.star;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.adapter.MyAdapter;
import cn.com.luckytry.interview.bean.InterViewInfo;
import cn.com.luckytry.interview.bean.InterViewUser;
import cn.com.luckytry.interview.ui.about.AboutActivity;
import cn.com.luckytry.interview.ui.main.MainActivity;
import cn.com.luckytry.interview.ui.widget.DividerItemDecoration;
import cn.com.luckytry.interview.ui.widget.SetDialog;
import cn.com.luckytry.interview.ui.widget.TitleItemDecoration;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

public class StarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initView();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.star_rvlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvInfo = (TextView) findViewById(R.id.tv_info);
        ArrayList<String> starData = BmobUser.getCurrentUser(InterViewUser.class).getStars();
        if(starData.size() == 0){
            mRecyclerView.setVisibility(View.GONE);
            tvInfo.setText("还没有收藏的数据呢！");
            tvInfo.setVisibility(View.VISIBLE);
        }else{
            BmobQuery<InterViewInfo> query = new BmobQuery<InterViewInfo>();
            //查询playerName叫“比目”的数据
            query.addWhereContainedIn("objectId", starData);
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(500);
            //执行查询方法
            query.findObjects(new FindListener<InterViewInfo>() {
                @Override
                public void done(List<InterViewInfo> data, BmobException e) {
                    if(e==null){
                        if(data.size()>0){
                            mRecyclerView.addItemDecoration(new TitleItemDecoration(StarActivity.this,data));
                            mRecyclerView.addItemDecoration(new DividerItemDecoration(StarActivity.this, DividerItemDecoration.VERTICAL_LIST));
                            mRecyclerView.setAdapter(new MyAdapter(StarActivity.this,R.layout.item_interview,data));
                        }else{
                            mRecyclerView.setVisibility(View.GONE);
                            tvInfo.setText("还没有收藏的数据呢！");
                            tvInfo.setVisibility(View.VISIBLE);
                        }

                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        tvInfo.setText(e.getMessage());
                        tvInfo.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            });
        }

//        List<InterviewBean> data = DataSupport.where("isStar = ?", 1+"").find(InterviewBean.class);
//        mRecyclerView.addItemDecoration(new TitleItemDecoration(this,data));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setAdapter(new MyAdapter(this,R.layout.item_interview,data));

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            Snackbar.make(drawer,"点击搜索",Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_star){

        }
        else if (id == R.id.nav_change) {
            int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            //切换主题
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPrefsUtil.putValue(MyApplication.getContext(), Const.THEME_MOUDLE,1);

            } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPrefsUtil.putValue(MyApplication.getContext(),Const.THEME_MOUDLE,2);

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
}
