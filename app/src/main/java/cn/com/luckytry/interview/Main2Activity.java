package cn.com.luckytry.interview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import cn.com.luckytry.interview.main.MainActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sendIntent();
    }

    public void doClick(View view){


    }

    private void sendIntent() {
        /**
         * 该方法虽然被抛弃过时，但是通用！
         */
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,
                        0, new Intent(this, MainActivity.class), 0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.play_notification);
        Notification notification = new Notification(R.mipmap.logo,
                "歌曲正在播放", System.currentTimeMillis());
        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;
        //标记位，设置通知栏一直存在
        notification.flags =Notification.FLAG_ONGOING_EVENT;

        Intent intent = new Intent("PlayService");
        intent.putExtra("BUTTON_NOTI", 1);
        PendingIntent preIntent = PendingIntent.getBroadcast(
                this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_pre, preIntent);

        intent.putExtra("BUTTON_NOTI", 2);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(
                this,
                2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_exit, pauseIntent);



        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //通知栏更新
        notificationManager.notify(5, notification);
    }


    public PendingIntent getDefalutIntent(int flags){
         PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
         return pendingIntent;
         }
}
