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
    public String getTitle(){
        return mSharedPreferences.getString(ARG_TITLE,mContext.getString(R.string.app_name));
    }
    public void setTitle(String title){
        mSharedPreferences.edit().putString(ARG_TITLE,title).apply();
    }


}
