package cn.com.luckytry.interview.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.luckytry.interview.IDownloadInterface;
import cn.com.luckytry.interview.bean.InterViewPath;
import cn.com.luckytry.interview.util.LUtil;
import cn.com.luckytry.interview.util.WavMergeUtil;

public class SynthesizeService extends Service {

    private ExecutorService singleThreadPool;
    // 语音合成对象
    private SpeechSynthesizer mTts;
    private Context mContext;

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {

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
    private IBinder binder = new IDownloadInterface.Stub()

    {

        @Override
        public int synthesizeToFile(int id, String info) throws RemoteException {
            LUtil.e("合成音频文件");
            singleThreadPool.execute(new SynthesizeRunnable(id,info));
            return id;
        }
    };

    public SynthesizeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();//59ad38c9  595f30c5
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59ad38c9");
        mContext = this;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
        singleThreadPool = Executors.newSingleThreadExecutor();
    }

    /**
     * 语音合成参数设置
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
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

    public class SynthesizeRunnable implements Runnable, SynthesizerListener {

        private int id;
        private String content;
        int count = 0;
        private List<String> texts;
        private String tempPath = Environment.getExternalStorageDirectory()+"/interview/temp/temp";
        private String path;
        private InterViewPath pathBean;
        public SynthesizeRunnable( int id,String content){

            this.id = id;
            this.content = content;
            path = Environment.getExternalStorageDirectory()+"/interview/specch/voice_"+id+".wav";
            pathBean = new InterViewPath();
            pathBean.setBeanId(id);

        }

        @Override
        public void run() {
            List<InterViewPath> data = DataSupport.where("beanId = ?", id + "").find(InterViewPath.class);
            if(data.size()>0){
                return;
            }else{

                texts = getvalue(content);

                final String tempPath = Environment.getExternalStorageDirectory()+"/interview/temp";
                if(texts.size()==1){
                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, path);
                    mTts.synthesizeToUri(texts.get(count),path,this);
                }else{
                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, tempPath+count+".wav");
                    mTts.synthesizeToUri(texts.get(count),tempPath+count+".wav",this);
                }
                count++;
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
        @Override
        public void onCompleted(SpeechError speechError) {
            if(speechError == null){
                if(texts.size()>=count){
                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, tempPath+count+".wav");
                    String mPath = tempPath+count+".wav";
                    mTts.synthesizeToUri(texts.get(count), mPath, this);
                    count++;
                }else{
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
                            pathBean.setPath(path);
                            pathBean.save();

                        } catch (IOException e) {

                        }
                    }else{
                        pathBean.setPath(path);
                        pathBean.save();

                    }
                }
            }
        }
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            LUtil.e("SynthesizeService---------onBufferProgress");
        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }
}
