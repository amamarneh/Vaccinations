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
            if (diff < min ){
                min = diff;
                choosenChild = c;
            }
        }

        Log.e("Amarneh","totify method:: min="+min);
        if (min != 1000){
            if (choosenChild != null){
                int r = NumberUtils.getRandomInt();
                if (vaccination.getNotificationId()==0){
                    vaccination.setNotificationId(r);
                    VacinationDB.getInstance(context).saveBean(vaccination);
                }
                long time = min * 24 * 60 * 60 * 1000;
                Calendar calendar = DateUtils.getCalendarFromDate(new Date(time));
                calendar.set(Calendar.HOUR_OF_DAY,12);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);

                AlarmsController.addFixedAlarm(context,calendar.getTimeInMillis(),vaccination.getNotificationId(),vaccination,choosenChild);
            }
        }
    }

}
