package com.amarnehsoft.vaccinations.database.firebase;

import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.StringsUtils;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBAd extends FirebaseHelper{
    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "ads";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Object bean,String query) {
        try {
            Ad info = (Ad) bean;
            return StringsUtils.like(info.getContent(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not ad class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public FBAd(Context context){
        super(Ad.class,context,true);
    }

    @Override
    protected boolean addBeanToList(Object bean) {
        try {
            Ad info = (Ad) bean;
            return  true;
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not ad class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
