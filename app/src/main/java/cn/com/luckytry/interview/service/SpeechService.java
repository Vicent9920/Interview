package cn.com.luckytry.interview.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.WavMergeUtil;

/**
 * 置于后台处理音频
 */
public class SpeechService extends Service {
    private static final String TAG = "SpeechService";

    // 语音合成对象
    private SpeechSynthesizer mTts;
    private Context mContext;
    private ExecutorService mExecutors;
    private int count = 0;
    private boolean isFile = false;
    private PowerManager.WakeLock mWakeLock = null;//获取设备电源锁，防止锁屏后服务被停止
    private Notification notification;//通知栏
    private RemoteViews remoteViews;//通知栏布局
    private NotificationManager notificationManager;
    private SensorManager mSensorManager;
    private MediaPlayer mPlayer;

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                LUtil.e(TAG,"初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                // 移动数据分析，收集开始合成事件
                FlowerCollector.onEvent(mContext, "tts_play");
                // 设置参数
                setParam();
            }
        }
    };


    public SpeechService() {
    }


    public class SpeechBinder extends Binder {
        public SpeechService getService() {
            return SpeechService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59ad38c9");
        acquireWakeLock();
        mContext = this;
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
        mPlayer = getMediaPlayer(this);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendMessage(-1);
            }
        });
        mExecutors = Executors.newSingleThreadExecutor();
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new SpeechBinder();
    }

    /**
     * 将文本处理为语音
     * @param textInfo
     * @param id
     */
    public void synthesizeToFile(String textInfo,int id){
        final InterviewBean bean = DataSupport.find(InterviewBean.class,id);
        final String path = Environment.getExternalStorageDirectory()+"/interview/specch/voice_"+id+".wav";
        count = 0;
        if(bean.getSpeechPath()!=null){
            isFile = true;
            try {
                mPlayer.setDataSource(path);
                mPlayer.prepare();
                mPlayer.setLooping(false);
                mPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            isFile = false;
            final List<String> texts = getvalue(textInfo);

            final String tempPath = Environment.getExternalStorageDirectory()+"/interview/temp";
            if(texts.size()==1){
                mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, path);
            }else
                mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, tempPath+count+".wav");
            int code = mTts.startSpeaking(texts.get(count), new SynthesizerListener() {
                //开始播放
                @Override
                public void onSpeakBegin() {
                   sendMessage(1);
                }
                //合成进度
                @Override
                public void onBufferProgress(int i, int i1, int i2, String s) {

                }
                //暂停播放
                @Override
                public void onSpeakPaused() {
                    sendMessage(2);
                }
                //继续播放
                @Override
                public void onSpeakResumed() {
                    sendMessage(1);
                }
                //播放进度
                @Override
                public void onSpeakProgress(int i, int i1, int i2) {

                }
                //合成结束
                @Override
                public void onCompleted(SpeechError speechError) {
                    if(speechError == null){
                        if(texts.size()>=count){
                            mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, tempPath+count+".wav");
                            String path = tempPath+count+".wav";
//                            int code = mTts.synthesizeToUri(texts.get(count), path, this);
                            int code = mTts.startSpeaking(texts.get(count), this);
                            count++;
                        }else{
                            sendMessage(-1);
                            if(texts.size()>1){
                                List<File> inputFiles = new ArrayList<>();
                                for (int i = 0; i < texts.size(); i++) {
                                    File file = new File(tempPath+i+".wav");
                                    inputFiles.add(file);
                                }
                                try {
                                    File outFile = new File(path);
                                    WavMergeUtil.mergeWav(inputFiles,outFile);
//                                    WavMergeUtil.getAllAudio(SpeechService.this,inputFiles,Environment.getExternalStorageDirectory()+"/msc/myvoice.wav");
                                    Log.e(TAG, "onCompleted 音频保存结束: " );
                                    bean.setSpeechPath(path);
                                    bean.save();

                                } catch (IOException e) {
                                    Log.e(TAG, "onCompleted: ",e );
                                }
                            }else{
                                bean.setSpeechPath(path);
                                bean.save();

                            }
                        }
                    }
                }
                //异常接口，供测试时使用
                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {

                }
            });
            count++;
        }

    }

    /**
     * 发送消息
     * @param code
     */
    private void sendMessage(int code) {
        Events<Integer> event = new Events();
        event.content = code;
        EventBus.getDefault().post(event);
    }

    /**
     * 暂停播放
     */
    public void pausePayler(){
        if(isFile){
            mPlayer.pause();
        }else{
            mTts.pauseSpeaking();
        }
    }

    /**
     * 继续播放
     */
    public void resumePlayer(){
        if(isFile){
            mPlayer.start();
        }else{
            mTts.resumeSpeaking();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlayer(){
        if(isFile){
            mPlayer.stop();
        }else{
            mTts.stopSpeaking();

        }
    }

    /**
     * 当前状态
     * @return
     */
    public boolean isPlay(){
        if(isFile){
            return true;
        }else{
            return mTts.isSpeaking();
        }
    }

    /**
     * 将字符串转为指定长短的字符串数组
     * @param text
     * @return
     */
    private List<String> getvalue(String text) {
        List<String> data = new ArrayList<>();

        if(text.length()<=3000){
            data.add(text);
        }else{
            int toal = text.length();
            int count = toal/3000;
            for (int i = 0; i < count; i++) {
                String info;
                if(i<count){
                    info = text.substring(i*3000,(i+1)*3000);
                    data.add(info);
                }
                if(i == count-1){
                    String info2 = text.substring((i+1)*3000,text.length());
                    data.add(info2);

                }


            }
        }
        return data;
    }

    /**
     * 语音合成参数设置
     */
    private void setParam(){
        // 清空参数
//        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "70");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");



    }


    /**
     * 通知栏设置
     */
    private void setNotification() {

/**
 * 该方法虽然被抛弃过时，但是通用！
 */

    }
    private MediaPlayer getMediaPlayer(Context context) {
        MediaPlayer mediaplayer = new MediaPlayer();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }
        try {
            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");
            Constructor constructor = cSubtitleController.getConstructor(
                    new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});
            Object subtitleInstance = constructor.newInstance(context, null, null);
            Field f = cSubtitleController.getDeclaredField("mHandler");
            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            } catch (IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }
            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor",
                    cSubtitleController, iSubtitleControllerAnchor);
            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        } catch (Exception e) {
            Log.d(TAG,"getMediaPlayer crash ,exception = "+e);
        }
        return mediaplayer;
    }

    // 申请设备电源锁
    private void acquireWakeLock() {

        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "");
            if (null != mWakeLock) {
                mWakeLock.acquire();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
        if(mTts!=null){
            mTts.destroy();
        }
        mContext = null;
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }



}
