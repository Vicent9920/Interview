package cn.com.luckytry.interview.ui.diyFile;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.luckytry.interview.MyApplication;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.jsbridge.BridgeHandler;
import cn.com.luckytry.interview.jsbridge.CallBackFunction;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.ui.widget.Kawaii_LoadingView;
import cn.com.luckytry.interview.ui.widget.ShowTextWebView;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener,ContentContract.View, BridgeHandler {

    private static final String TAG = "ContentActivity";
    private ShowTextWebView mWebView;
    // 1. 定义控件变量
    private Kawaii_LoadingView mLoadingView;

    private CollapsingToolbarLayout mToolbarLayout;

    private Handler mHandler;
    public String tag;
    public String name;
    private TextView textView;
    private BottomSheetDialog mBottomSheetDialog;
    private Toast toast;


    /****************************************** Presenter ********************************************************/
    private ContentPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        tag = getIntent().getStringExtra("tag");
        name = getIntent().getStringExtra("name");

        mPresenter = new ContentPresenter();
        mPresenter.setView(this);
        mHandler = new Handler();
        initViews();

        setCollapsingToolbarLayoutTitle(name);
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        bindService(new Intent(this,SpeechService.class),mServiceConnection, Context.BIND_AUTO_CREATE);
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
            case R.id.action_voice:
                if(mPresenter.isOnLoad()){
                    toast.setText("请页面加载完成后播放");
                    toast.show();
                }else{
                    mPresenter.controlPlay();
                }

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
        mPresenter.start();
        mLoadingView.startMoving();
        int mode = SharedPrefsUtil.getValue(MyApplication.getContext(), Const.THEME_MOUDLE,1,false);
        if(mode!=1){
            mWebView.callHandler("theme","",null);
        }
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
                        mBottomSheetDialog.dismiss();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }else{
            if(mPresenter.isStar()){
                ivStar.setImageResource(R.mipmap.star);
                tvStar.setText("取消收藏");
            }else{
                ivStar.setImageResource(R.mipmap.unstar);
                tvStar.setText("添加到收藏");
            }
            mBottomSheetDialog.show();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mPresenter.onDestory();

    }

    /**
     * 准备退出
     */
    private void parpreBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setMessage("确定退出播放？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        onBackPressed();
                        mPresenter.stopPlayer();
                    }
                })
                .setNegativeButton("后台播放", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        onBackPressed();
                        mPresenter.sendNotification(tag,name);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fab_shar){
            mPresenter.sharLink(name);
        }else{
            if(mPresenter.isPlay()){
                parpreBack();
            }else{
                onBackPressed();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(mPresenter.isPlay()){
                parpreBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void doClick(View view){
        String tag = (String) view.getTag();

        switch (tag){
            case "star":
                boolean state = mPresenter.addStar();
                String info = "已从收藏中删除";
                if(state){
                    info = "已添加到收藏";
                }
                Snackbar.make(mWebView,info,Snackbar.LENGTH_SHORT).show();
                break;
            case "link":
                mPresenter.copyLink();
                break;
            case "browser":
                mPresenter.openByBrowser();
                break;
            case "copy":
                mPresenter.copyText();
                break;
        }
        mBottomSheetDialog.dismiss();
    }






    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SpeechService mSpeechService = ((SpeechService.SpeechBinder) service).getService();
            mPresenter.setSpeechService(mSpeechService);

        }
    };

    @Override
    public void initViews() {
        //头部
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back2);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView = (TextView) findViewById(R.id.tv_name);
        findViewById(R.id.fab_shar).setOnClickListener(this);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(mPresenter.getAppBarStateChangeListener() );
//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ffffffff"),Color.parseColor("#ffffffff"));
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mWebView = (ShowTextWebView) findViewById(R.id.web_view);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.setDefaultHandler(this);
        mLoadingView = (Kawaii_LoadingView) findViewById(R.id.Kawaii_LoadingView);
        mPresenter.getmBean(name);

        mWebView.setOnChangeListener(mPresenter.getOnChangeListener());

    }

    private void reresh() {
        mPresenter.parpreSource();
        mWebView.setVisibility(View.GONE);
//        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }



    @Override
    public void onTitle(int state) {
        if( state == ContentPresenter.AppBarStateChangeListener.EXPANDED ) {
            mToolbarLayout.setTitle("");
            //展开状态

        }else if(state == ContentPresenter.AppBarStateChangeListener.COLLAPSED){
            mToolbarLayout.setTitle(name);
            //折叠状态

        }else {
            mToolbarLayout.setTitle(name);
            //中间状态

        }
    }



    @Override
    public void showSource(String source) {

        mWebView.loadingUrl(source);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }


    @Override
    public void handler(String name, String data, CallBackFunction function) {
        switch (name){
            case "resultText":
                mWebView.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                break;
        }

    }
}