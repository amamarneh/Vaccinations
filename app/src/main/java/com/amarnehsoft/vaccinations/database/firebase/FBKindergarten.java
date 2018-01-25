package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.StringsUtils;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBKindergarten extends FirebaseHelper{
    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "kindergarten";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Object bean,String query) {
        try {
            Kindergarten info = (Kindergarten) bean;
            return StringsUtils.like(info.getName(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not Kindergarten class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public FBKindergarten(Context context){
        super(Kindergarten.class,context,true);
    }

    @Override
    protected boolean addBeanToList(Object bean) {
        try {
            Kindergarten info = (Kindergarten) bean;
            return  true;
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not Kindergarten class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
