package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.utils.StringsUtils;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBStocks extends FirebaseHelper{
    private int type;

    public FBStocks(Context context,int type){
        super(Stock.class,context,true);
        this.type = type;
    }

    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "stocks";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Object bean,String query) {
        try {
            Stock info = (Stock) bean;
            return StringsUtils.like(info.getName(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not Stock class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public FBStocks(Context context){
        super(Stock.class,context,true);
    }

    @Override
    protected boolean addBeanToList(Object bean) {
        try {
            Stock info = (Stock) bean;
            if (info.getType()==type)
                return  true;
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not Stock class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
