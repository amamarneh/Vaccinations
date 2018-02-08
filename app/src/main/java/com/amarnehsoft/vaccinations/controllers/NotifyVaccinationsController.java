package com.amarnehsoft.vaccinations.controllers;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.DateUtils;
import com.amarnehsoft.vaccinations.utils.NumberUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jcc on 1/12/2018.
 */

public class NotifyVaccinationsController {

    public static void notify(Context context,Vaccination vaccination){
        Log.e("Amarneh","inside notify method");
        List<Child> children = ChildDB.getInstance(context).getAll();
        int min=1000;
        Child choosenChild = null;
        for (Child c : children){
            int diff=-1;
            if (vaccination.getManuallySet() == 0){
                diff = vaccination.getAge() - DateUtils.getAgeInDays(c.getBirthDate());
            }else {
                diff = vaccination.getNewAge() - DateUtils.getAgeInDays(c.getBirthDate());
            }
            if (diff < min  && diff >= 0){
                min = diff;
                choosenChild = c;
            }
        }

        Log.e("Amarneh","totify method:: min="+min);
        if (min != 1000 && min >= 0){
            if (choosenChild != null){
                int r = NumberUtils.getRandomInt();
                if (vaccination.getNotificationId()==0){
                    vaccination.setNotificationId(r);
                    VacinationDB.getInstance(context).saveBean(vaccination);
                }
                int seconds = min * 24 * 60 * 60;
                Calendar calendar = DateUtils.getCalendarFromDate(DateUtils.incrementDateBySeconds(seconds));
                calendar.set(Calendar.HOUR_OF_DAY,12);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);

                if (calendar.getTime().getTime() > new Date().getTime()){
                    AlarmsController.addFixedAlarm(context,calendar.getTimeInMillis(),vaccination.getNotificationId(),vaccination,choosenChild);
                }
            }
        }
    }


    public static void notifyDate(Context context,Vaccination vaccination,Child child){
            int min = vaccination.getAge() - DateUtils.getAgeInDays(child.getBirthDate());

        if (min >= 0){
            if (true){
                if (vaccination.getNotificationId()==0){
                    vaccination.setNotificationId(NumberUtils.getRandomInt());
                    VacinationDB.getInstance(context).saveBean(vaccination);
                }
                int seconds = min * 24 * 60 * 60;
                Calendar calendar = DateUtils.getCalendarFromDate(DateUtils.incrementDateBySeconds(seconds));
                calendar.set(Calendar.HOUR_OF_DAY,12);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);

                if (calendar.getTime().getTime() > new Date().getTime()){
                    AlarmsController.addFixedAlarm(context,calendar.getTimeInMillis(),vaccination.getNotificationId(),vaccination,child);
                }
            }
        }
    }


}
