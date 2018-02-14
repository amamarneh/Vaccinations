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
import com.amarnehsoft.vaccinations.database.db2.DBVaccination;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.DBVacChild;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.DateUtils;

import java.util.Date;

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
        Log.d("tag","vaccination="+intent.getStringExtra("vaccination"));
        Log.d("tag","child="+intent.getStringExtra("child"));
//        Vaccination vaccination = intent.getParcelableExtra("vaccination");
//        Child child = intent.getParcelableExtra("child");
//
        String vacCode = intent.getStringExtra("vaccination");
        String childCode = intent.getStringExtra("child");
        Vaccination vaccination = DBVaccination.getInstance(context).getBeanById(vacCode);
            if(vaccination == null)
                vaccination = VacinationDB.getInstance(context).getBeanById(vacCode);
        Child child = ChildDB.getInstance(context).getBeanById(childCode);

        Notification notification = null;
//
        Date date = DBVacChild.getInstance(context).getBeanByVacChild(childCode,vacCode).getDate();
        int diffDays = DateUtils.getDiffDays(date,new Date());
//        if (vaccination.getManuallySet()==0){
//            diffDays = vaccination.getAge() - DateUtils.getAgeInDays(child.getBirthDate());
//        }else {
//            diffDays = vaccination.getNewAge() - DateUtils.getAgeInDays(child.getBirthDate());
//        }
//
        String title="";
//        if (vaccination.getType()==Vaccination.TYPE_VACCINATION){
            title = context.getString(R.string.vaccinationDateForYourChild) + " (" +   child.getName() + ")";
//        }else {
//            title = context.getString(R.string.dateForYourChild) + " (" +   child.getName() + ")";
//        }
//
        String content = context.getString(R.string.yourChild) + " ("+child.getName()+") ";
//
//        if (vaccination.getType()==Vaccination.TYPE_VACCINATION){
            content += context.getString(R.string.haveToTakeTheVaccination);
//        }else {
//            content += context.getString(R.string.haveADate);
//        }
//
        if(vaccination != null){
            content += " (" + vaccination.getName()+") " +
                     context.getString(R.string.after) + " " + diffDays + " " + context.getString(R.string.days) + " ("+DateUtils.formatDateWithoutTime(DateUtils.incrementDateByDays(diffDays)) + ")";
        }
            notification = NotificationController.getNotification(context,title,content);


        notificationManager.notify(code,notification);
    }
}

