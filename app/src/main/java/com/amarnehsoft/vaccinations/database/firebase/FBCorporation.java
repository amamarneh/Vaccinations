package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBCorporation extends FirebaseHelper<Corporation>{
    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "corporations";
        }
        return null;
    }
    public static DatabaseReference getDataRef(){
        return FirebaseDatabase.getInstance().getReference().child("corporations");
    }

    @Override
    protected boolean addBeanToSearchList(Corporation bean,String query) {
        try {
            return StringsUtils.like(bean.getName(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not corporation class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public FBCorporation(Context context){
        super(Corporation.class,context,true);
    }
}
