package com.amarnehsoft.vaccinations.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.VacChild;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.db2.DBVaccination;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.DBVacChild;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.services.AlarmReceiver;

import java.util.Date;
import java.util.List;

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
    public static void deleteAlarm(Context context,int code){
        Log.d("Amarneh","Deleting Alarm, code="+code);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    public static void deleteAllAlarmsNotManualSet(Context context){
        List<VacChild> vacChildren = DBVacChild.getInstance(context).getAllNotManualSet();
        for (VacChild v :
                vacChildren) {
            deleteAlarm(context,v.getId());
        }
    }
    public static void deleteAllAlarms(Context context){
        List<VacChild> vacChildren = DBVacChild.getInstance(context).getAll();
        for (VacChild v :
                vacChildren) {
            deleteAlarm(context,v.getId());
        }
    }
    public static void notifyAllVaccinations(Context context){
        List<VacChild> vacChildren = DBVacChild.getInstance(context).getAll();
        for (VacChild v :
                vacChildren) {
            Vaccination vaccination = DBVaccination.getInstance(context).getBeanById(v.getVacCode());
            if(vaccination == null)
                vaccination = VacinationDB.getInstance(context).getBeanById(v.getVacCode());
            Child c = ChildDB.getInstance(context).getBeanById(v.getChildCode());
            // there is a real vaccination ( has not been removed)
            if(vaccination != null && c!=null){
                addFixedAlarm(context,v,SPController.getInstance(context).getHourOfDay(),SPController.getInstance(context).getMinute(),v.getManualSet() == 0);
            }
        }
    }

    //unused
    public static void addFixedAlarm(Context context, long time, int code, Vaccination vaccination, Child child){
        Log.d("tag","addFixedAlarm...");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(code+"");
        intent.putExtra("vaccination",vaccination);
        intent.putExtra("child",child);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }

    public static void addFixedAlarm(Context context, VacChild vacChild, int h, int m, boolean addTime){
        VacChild vc = DBVacChild.getInstance(context).getBeanByVacChild(vacChild.getChildCode(),vacChild.getVacCode());


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(vc.getId()+"");
        intent.putExtra("vaccination",vc.getVacCode());
        intent.putExtra("child",vc.getChildCode());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, vc.getId(), intent, 0);

        Date date = vc.getDate();
        if(addTime){
        date.setHours(h);
        date.setMinutes(m);
        }

        if(date.after(new Date())){
            alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTime(),pendingIntent);
            Log.d("tag","addFixedAlarm date="+date.toString() + " code="+vc.getId());
        }else{

        Log.d("tag","DIDN'T addFixedAlarm date="+date.toString() + " code="+vc.getId());
        }



    }
}