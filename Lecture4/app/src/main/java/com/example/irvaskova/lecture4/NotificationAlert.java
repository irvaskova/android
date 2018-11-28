package com.example.irvaskova.lecture4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.ContextWrapper;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

public class NotificationAlert extends ContextWrapper {
    private static final String CHANNEL_ID="com.example.irvaskova.lecture4";
    private static final String CHANNEL_NAME="NOTIFICATION Channel";
    private NotificationManager manager;
    public NotificationAlert(Context base){
        super(base);
        createChannels();
    }
    private void createChannels(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.DKGRAY);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (manager==null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            return manager;
    }

    public Notification.Builder getChannelNotifications(String title, String body){
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
}
