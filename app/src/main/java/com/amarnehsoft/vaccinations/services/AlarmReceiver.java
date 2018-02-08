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
import com.amarnehsoft.vaccinations.utils.DateUtils;

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

        int diffDays=0;
        if (vaccination.getManuallySet()==0){
            diffDays = vaccination.getAge() - DateUtils.getAgeInDays(child.getBirthDate());
        }else {
            diffDays = vaccination.getNewAge() - DateUtils.getAgeInDays(child.getBirthDate());
        }

        String title="";
        if (vaccination.getType()==Vaccination.TYPE_VACCINATION){
            title = context.getString(R.string.vaccinationDateForYourChild) + " (" +   child.getName() + ")";
        }else {
            title = context.getString(R.string.dateForYourChild) + " (" +   child.getName() + ")";
        }

        String content = context.getString(R.string.yourChild) + " ("+child.getName()+") ";

        if (vaccination.getType()==Vaccination.TYPE_VACCINATION){
            content += context.getString(R.string.haveToTakeTheVaccination);
        }else {
            content += context.getString(R.string.haveADate);
        }

        content += " (" + vaccination.getName()+") " +
                 context.getString(R.string.after) + " " + diffDays + " " + context.getString(R.string.days) + " ("+DateUtils.formatDateWithoutTime(DateUtils.incrementDateByDays(diffDays)) + ")";
        notification = NotificationController.getNotification(context,title,content);


        notificationManager.notify(code,notification);
    }
}

