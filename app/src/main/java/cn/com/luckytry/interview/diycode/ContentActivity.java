package cn.com.luckytry.interview.diycode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.List;

import cn.com.luckytry.interview.IDownloadInterface;
import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.service.SpeechService;
import cn.com.luckytry.interview.service.SynthesizeService;
import cn.com.luckytry.interview.util.Const;
import cn.com.luckytry.interview.util.LUtil;
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
    private TextView textView;
    private String url;
    private BottomSheetDialog mBottomSheetDialog;
    private Toast toast;

    /****************************************** 语音播放 ********************************************************/
    private SpeechService mSpeechService;
    private String content = null;
    private int playState = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mHandler = new Handler();
        initView();
        tag = getIntent().getStringExtra("tag");
        name = getIntent().getStringExtra("name");
        setCollapsingToolbarLayoutTitle(name);
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        bindService(new Intent(this,SpeechService.class),mServiceConnection,Context.BIND_AUTO_CREATE);
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

            if(mBean.isValidContent()){
                mWebView.isShowSource();
                String source = Const.getData(ContentActivity.this,mBean.getContent());
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
                mBean.saveAsync().listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        mBean.setValidContent(true);
                        mBean.saveAsync();
                    }
                });
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
        mWebView.setOnGetTextListener(new ShowTextWebView.OnGetTextListener() {
            @Override
            public void onGetText(String text) {
                content = text;

                serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        IDownloadInterface mDownloadInterface = IDownloadInterface.Stub.asInterface(service);
                        try {
                            int result = mDownloadInterface.synthesizeToFile(mBean.getId(),content);
                            LUtil.e("连接跨进程"+result);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            LUtil.e("连接跨进程",e);
                        }
                    }
                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                bindService(new Intent(ContentActivity.this, SynthesizeService.class),serviceConnection,Context.BIND_AUTO_CREATE);

            }
        });
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
                if(content == null){
                    toast.setText("请页面加载完成后播放");
                    toast.show();
                }else{
                    controlPlay();

                }

            default:
                break;
        }
//         Toast.makeText(MainActivity.this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    /**
     * 控制播放
     */
    private void controlPlay() {
        if(playState == -1){//停止状态
            try {
                mSpeechService.synthesizeToFile(content,mBean.getId());
                playState = 1;
            } catch (Exception e) {
                e.printStackTrace();
                LUtil.e("controlPlay",e);
            }
        }else if(playState == 1){//播放状态
            mSpeechService.pausePayler();
            playState++;
        }else if(playState == 2){//暂停状态
            mSpeechService.resumePlayer();
            playState = 1;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayStaty(Events<Integer> event) {
        playState = event.content;
    };


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        mLoadingView.startMoving();
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
            if(mBean.isStar()){
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        unbindService(serviceConnection);
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
                        mSpeechService.stopPlayer();
                    }
                })
                .setNegativeButton("后台播放", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        onBackPressed();
                        mSpeechService.setNotification(url,tag,name);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.fab_shar){
            sharLink();
        }else{
            if(mSpeechService.isPlay()){
                parpreBack();
            }else{
                onBackPressed();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(mSpeechService.isPlay()){
                parpreBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void doClick(View view){
        String tag = (String) view.getTag();
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (tag){
            case "star":
                boolean state = mBean.isStar();
                mBean.setStar(!state);
                mBean.save();

                String info = "已添加到收藏";
                if(state){
                    info = "已从收藏中删除";
                }

                Snackbar.make(mWebView,info,Snackbar.LENGTH_SHORT).show();
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

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mSpeechService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mSpeechService = ( (SpeechService.SpeechBinder)service).getService();

        }
    };


    private ServiceConnection serviceConnection ;

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
