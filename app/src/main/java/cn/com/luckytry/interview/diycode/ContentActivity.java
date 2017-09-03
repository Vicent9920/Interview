package cn.com.luckytry.interview.diycode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.SharedPrefsUtil;
import cn.com.luckytry.interview.view.Kawaii_LoadingView;
import cn.com.luckytry.interview.view.ShowTextWebView;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ContentActivity";
    private NestedScrollView mScrollView;
    private ShowTextWebView mWebView;
    // 1. 定义控件变量
    private Kawaii_LoadingView mLoadingView;

    private CollapsingToolbarLayout mToolbarLayout;

    private boolean isRefresh = false;
    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String tag;
    private String name;
    private InterviewBean mBean;
    public static int mode;
    private TextView textView;
    private String url;
    private BottomSheetDialog mBottomSheetDialog;

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
//        EventBus.getDefault().register(this);
        mode = SharedPrefsUtil.getValue(MyApplication.getContext(), Const.THEME_MOUDLE,1,false);
        mLoadingView.startMoving();
    }


    private void initView() {
        //头部
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back2);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView = (TextView) findViewById(R.id.tv_name);
        findViewById(R.id.fab_shar).setOnClickListener(this);
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
                    mToolbarLayout.setTitle("");
                    //展开状态

                }else if(state == State.COLLAPSED){
                    mToolbarLayout.setTitle(name);
                    //折叠状态

                }else {
                    mToolbarLayout.setTitle(name);
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
        mLoadingView = (Kawaii_LoadingView) findViewById(R.id.Kawaii_LoadingView);

        url = getIntent().getStringExtra("url");

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
        mWebView.setResultCall(new ShowTextWebView.OnResultCall() {
            @Override
            public void load(String html) {
                mBean.setContent(html);
                mBean.saveAsync();
                mWebView.setVisibility(View.VISIBLE);

                mLoadingView.setVisibility(View.GONE);
//                mLoadingView.stopMoving();
            }
        });
        mWebView.setOnChangeListener(new ShowTextWebView.OnChangeListener() {
            @Override
            public void onProgressChanged(int newProgress) {
                LUtil.e("Progress："+newProgress);

            }

            @Override
            public void onReceivedError(WebResourceError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case R.id.action_more:
                showShareDialog();
                break;

            default:
                break;
        }
//         Toast.makeText(MainActivity.this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }


    private ImageView ivStar;
    private TextView tvStar;
    /**
     * menu dialog
     */
    private void showShareDialog(){
        if(mBottomSheetDialog == null){
            mBottomSheetDialog = new BottomSheetDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_more_dialog,null);
            tvStar = (TextView) view.findViewById(R.id.tv_star);
            ivStar = (ImageView) view.findViewById(R.id.iv_star);
            mBottomSheetDialog.setContentView(view);
            mBottomSheetDialog.setCancelable(true);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);
            // 解决下滑隐藏dialog 后，再次调用show 方法显示时，不能弹出Dialog
            View view1 = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
            final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view1);
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        Log.i("BottomSheet","onStateChanged");
                        mBottomSheetDialog.dismiss();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }else{
            mBottomSheetDialog.show();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }



    private void setCollapsingToolbarLayoutTitle(String title) {
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

        textView.setText(tag);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_shar){
            sharLink();
        }else
        onBackPressed();
    }

    public void doClick(View view){
        String tag = (String) view.getTag();
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (tag){
            case "star":

                mBean.setStar(!mBean.isStar());
                break;
            case "link":
                // 创建普通字符型ClipData
                ClipData mCliplLink = ClipData.newPlainText("Label", url);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mCliplLink);
                break;
            case "browser":

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case "copy":
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", mBean.getContent());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                break;
        }
        mBottomSheetDialog.dismiss();
    }



    /**
     * 分享链接
     */
    private void sharLink() {
        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "面试宝典");

        share_intent.putExtra(Intent.EXTRA_TEXT, name+url+"分享自面试宝典·Android" );

        share_intent = Intent.createChooser(share_intent, "分享");

        startActivity(share_intent);
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
