package com.amarnehsoft.vaccinations.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.services.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by jcc on 1/12/2018.
 */


public class AlarmsController {
    public final static int CODE_EXPORT = 1;
    public static void AddAlarm(Context context, int code, long freq){
        Log.e("Amarneh","Adding Alarm - freq = " + freq);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        long delay = 1000*5;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + freq,freq,pendingIntent);
    }
    public static void DeleteAlarm(Context context,int code){
        Log.d("Amarneh","Deleting Alarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public static void addFixedAlarm(Context context, long time, int code, Vaccination vaccination, Child child){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(code+"");
        intent.putExtra("vaccination",vaccination);
        intent.putExtra("child",child);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + time,pendingIntent);
    }
}