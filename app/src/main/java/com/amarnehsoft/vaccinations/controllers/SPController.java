package com.amarnehsoft.vaccinations.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.utils.StringsUtils;

/**
 * Created by jcc on 2/5/2018.
 */

public class SPController {
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private static SPController instance;
    public static SPController getInstance(Context context){
        if (instance == null)
            instance = new SPController(context);
        return instance;
    }

    private SPController(Context context){
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("private",Context.MODE_PRIVATE);
    }

    private static final String ARG_TITLE="title";
    private static final String ARG_H="h";
    private static final String ARG_M="m";
    private static final String ARG_LAST_AD_POSITION="lastAdPosition";

    public String getTitle(){
        return mSharedPreferences.getString(ARG_TITLE,mContext.getString(R.string.app_name));
    }
    public void setTitle(String title){
        mSharedPreferences.edit().putString(ARG_TITLE,title).apply();
    }
    public void saveHourOfDay(int h){
        mSharedPreferences.edit().putInt(ARG_H,h).apply();
    }
    public void saveMinute(int m){
        mSharedPreferences.edit().putInt(ARG_M,m).apply();
    }
    public int getHourOfDay(){
        return mSharedPreferences.getInt(ARG_H,5);
    }
    public int getMinute(){
        return mSharedPreferences.getInt(ARG_M,5);
    }

    public void setLastAdPosition(int val){
        mSharedPreferences.edit().putInt(ARG_LAST_AD_POSITION,val).apply();
    }

    public int getLastAdPosition(){
        return mSharedPreferences.getInt(ARG_LAST_AD_POSITION,0);
    }
}
