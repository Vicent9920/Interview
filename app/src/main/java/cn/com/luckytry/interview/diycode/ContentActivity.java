package cn.com.luckytry.interview.diycode;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.List;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;
import cn.com.luckytry.interview.view.ShowTextWebView;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ContentActivity";
    private NestedScrollView mScrollView;
    private ShowTextWebView mWebView;
    private CollapsingToolbarLayout mToolbarLayout;
    private RelativeLayout prepareLayout;
    private ProgressBar mProgressBar;
    private ImageView ivError;
    private TextView tvError;
    private boolean isRefresh = false;
    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String tag;
    private String name;
    private InterviewBean mBean;
    public static int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mHandler = new Handler();
        initView();
        tag = getIntent().getStringExtra("tag");
        name = getIntent().getStringExtra("name");
        setCollapsingToolbarLayoutTitle(name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mode = SharedPrefsUtil.getValue(MyApplication.getContext(), Const.THEME_MOUDLE,1,false);
    }


    private void initView() {
        //头部
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back2);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = (TextView) findViewById(R.id.tv_name);
        textView.setText(tag);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.smoothScrollTo(0,0);
            }
        });
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener(){

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
//                    mToolbarLayout.setTitle("");
                    //展开状态

                }else if(state == State.COLLAPSED){
//                    mToolbarLayout.setTitle(tag);
                    //折叠状态

                }else {
//                    mToolbarLayout.setTitle(tag);
                    //中间状态

                }
            }
        } );
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    },1000);
                }
            }
        });
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mWebView = (ShowTextWebView) findViewById(R.id.web_view);

        String url = getIntent().getStringExtra("url");

        List<InterviewBean> beans = DataSupport.where("adress = ?", url).find(InterviewBean.class);
        if (beans.size()>0){
            mBean = beans.get(0);

            if(mBean.getContent()!=null){
                mWebView.isShowSource();
                String source = Const.getData(mBean.getContent());
                mWebView.loadUrl(source);
                mWebView.setVisibility(View.VISIBLE);
            }else{
                if(url.startsWith("http://www.jianshu.com")){
                    mWebView.isJianShu();
                    mWebView.loadUrl(url);
                }else{
                    mWebView.loadUrl(mBean.getAdress());
                }

            }

        }else{
            if(url.startsWith("http://www.jianshu.com")){
                mWebView.isJianShu();
                mWebView.loadUrl(url);
            }else{
                mWebView.loadUrl(mBean.getAdress());
            }
        }
        mWebView.setResultCall(new ShowTextWebView.onResultCall() {
            @Override
            public void load(String html) {
                mBean.setContent(html);
                mBean.saveAsync();
                mWebView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events<Integer> event) {
//        if(event.content == 100){
//            mProgressBar.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            prepareLayout.setVisibility(View.GONE);
//        }else{
//            mProgressBar.setVisibility(View.VISIBLE);
//            mProgressBar.setProgress(event.content);//设置加载进度
//        }

    }

    private void setCollapsingToolbarLayoutTitle(String title) {
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    abstract static class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
        public enum State {
            EXPANDED,
            COLLAPSED,
            IDLE
        }

        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }
}
