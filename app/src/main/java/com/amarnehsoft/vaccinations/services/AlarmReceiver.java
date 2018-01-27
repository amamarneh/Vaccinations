package com.amarnehsoft.vaccinations.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.controllers.NotificationController;

/**
 * Created by jcc on 1/12/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    // remianing 10 mints for the car;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Amarneh","Alarm Received! - Exporting");
        Log.e("Amarneh","action: " + intent.getAction());

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int code = Integer.parseInt(intent.getAction());
        Vaccination vaccination = intent.getParcelableExtra("vaccination");
        Child child = intent.getParcelableExtra("child");

        Notification notification = null;
        String content = "content";
        notification = NotificationController.getNotification(context,"title",content);


        notificationManager.notify(code,notification);
    }
}

