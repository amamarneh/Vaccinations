package com.amarnehsoft.vaccinations.utils;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by jcc on 8/22/2017.
 */

public class DateUtils {
    public static final int DAY_SAT=0;
    public static final int DAY_SUN=1;
    public static final int DAY_MON=2;
    public static final int DAY_TUE=3;
    public static final int DAY_WED=4;
    public static final int DAY_THU=5;
    public static final int DAY_FRI=6;

    public static Date getDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        return date;
    }
    public static CharSequence getRelative(Date date){
        return  android.text.format.DateUtils.getRelativeTimeSpanString(date.getTime(),new Date().getTime(),android.text.format.DateUtils.DAY_IN_MILLIS,android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE);

    }
    public static String formatDate(Date date){
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d/MM/yyyy h:mm a", Locale.getDefault());
        return dateFormat.format(date);
    }
    public static String formatTime(Date date){
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return dateFormat.format(date);
    }
    public static String formatDateWithoutTime(Date date){
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d/MM/yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }
    public static Calendar getCalendarFromDate(Date date){
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int compare(Date src, Date dest){
         if (src.before(dest)){
             return -1;
         }

         if (src.after(dest)){
             return 1;
         }

         return 0;
    }

    public static int getDiffDays(Date date1, Date date2) {
        //if date1 > date2 => positive , else => negative
        Calendar calendar1= Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        long msDiff = calendar1.getTimeInMillis()-calendar2.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
        Log.e("Amarneh","diffDays>>"+daysDiff);
        return (int)daysDiff;
    }

    public static long getDiffInMilliSeconds(Date date1, Date date2){
        //if date1 > date2 => positive , else => negative
        return date1.getTime() - date2.getTime();
    }

    public static int getAgeInDays(Date birthdate){
        return getDiffDays(new Date(),birthdate);
    }

    public static long getDiffSeconds(Date date1,Date date2) {
        if (date1 == null || date2== null)
            return 0;
        //if date1 > date2 => positive , else => negative

        Calendar calendar1= Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        long msDiff = calendar1.getTimeInMillis()-calendar2.getTimeInMillis();
        long diff = TimeUnit.MILLISECONDS.toSeconds(msDiff);
        Log.e("Amarneh","diffDays>>"+diff);
        return diff;
    }

    public static Date decrementDateBySeconds(int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTime ( new Date() ); // convert your date to Calendar object
        int secondsToDecrement = -seconds;
        cal.add(Calendar.SECOND, secondsToDecrement);
        return cal.getTime(); // again get back your date object
    }

    public static Date incrementDateBySeconds(int seconds){
        return incrementDateBySeconds(new Date(),seconds);
    }

    public static Date incrementDateBySeconds(Date date,int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTime ( date ); // convert your date to Calendar object
        int secondsToIncrement = seconds;
        cal.add(Calendar.SECOND, secondsToIncrement);
        return cal.getTime(); // again get back your date object
    }

    public static Date incrementDateByDays(Date date,int days){
        int s = days * 24 * 60 * 60 ;
        return incrementDateBySeconds(date,s);
    }

    public static Date incrementDateByDays(int days){
        return incrementDateByDays(new Date(),days);
    }

    public static String getDayStr(Context context,int day){
        if (day == DAY_SAT){
            return context.getString(R.string.sat);
        }
        if (day == DAY_SUN){
            return context.getString(R.string.sun);
        }
        if (day == DAY_MON){
            return context.getString(R.string.mon);
        }
        if (day == DAY_TUE){
            return context.getString(R.string.tue);
        }
        if (day == DAY_WED){
            return context.getString(R.string.wed);
        }if (day == DAY_THU){
            return context.getString(R.string.thu);
        }
        if (day == DAY_FRI){
            return context.getString(R.string.fri);
        }
        return "";
    }

    public static String formatAge(Context context,int days){
            int years = (int)(days / 365.25);
            int months = (int)((days % 365.25) /30.5);
            int day = (int)((days % 365.25) % 30.5);

            if (years == 0){
                if (months == 0){
                    return day + context.getString(R.string.days);
                }else {
                    if (day == 0){
                        return months + " " + context.getString(R.string.monthes);
                    }else {
                        return months + " " + context.getString(R.string.monthes)+ " " + context.getString(R.string.and) + " " + day + " " + context.getString(R.string.days);
                    }
                }
            }else {
                if (months != 0){
                    if (day != 0){
                        return years + " " + context.getString(R.string.years) + " " + context.getString(R.string.and) + " " + months + " " + context.getString(R.string.monthes) + " " + context.getString(R.string.and) + " " + day + " " + context.getString(R.string.days);
                    }else {
                        return years + " " + context.getString(R.string.years) + " " + context.getString(R.string.and) + " " + months;
                    }
                }else {
                    if (day == 0){
                        return years + " " + context.getString(R.string.years);
                    }else {
                        return years + " " + context.getString(R.string.years) + " " + context.getString(R.string.and) + " " + day + " " + context.getString(R.string.days);
                    }
                }
            }
    }
}
