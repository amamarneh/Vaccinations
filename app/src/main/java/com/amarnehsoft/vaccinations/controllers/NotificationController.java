package com.amarnehsoft.vaccinations.controllers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.SplashActivity;
import com.amarnehsoft.vaccinations.broadcastReceiverss.NotificationPublisher;

/**
 * Created by jcc on 1/12/2018.
 */

public class NotificationController {
    public static void scheduleNotification(Context context, Notification notification, long delay, int id) {
        Intent intent = NotificationPublisher.newIntent(context,notification,id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent , 0);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public static Notification getNotification(Context context,String title,String content) {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher_background);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, content);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content));
//                .setContent(contentView);



        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);

        Notification notification = mBuilder.build();
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        return notification;
    }
}