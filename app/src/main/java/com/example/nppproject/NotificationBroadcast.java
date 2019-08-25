package com.example.nppproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationBroadcast extends BroadcastReceiver {

    private static final String TAG = "NotificationBroadcast";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    //    @TargetApi(Build.VERSION_CODES.O)
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_NotificationBroadcast(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        String CHANNEL_ID = "My Notification";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, 0);
//        Notification notification = new Notification.Builder(mContext, CHANNEL_ID).setContentText("Có bài viết mới").setContentTitle("Mở App")
//                .setContentIntent(pendingIntent);
    }
}
