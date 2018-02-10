package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBCat extends FirebaseHelper<Cat>{
    private String catsCodes;

    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "cats";
        }
        return null;
    }
    public static DatabaseReference getDataRef(){
        return FirebaseDatabase.getInstance().getReference().child("cats");
    }

    @Override
    protected boolean addBeanToSearchList(Cat bean,String query) {
        return StringsUtils.like(bean.getName(),"%"+query+"%");
    }

    @Override
    protected boolean addBeanToList(Cat bean) {
        Log.e("Amarneh","FBCat.addBeanToList,name="+bean.getName()+",catsCodes="+catsCodes);
        if (catsCodes==null) return true;
        for (String code : Corporation.getCatsFromString(catsCodes)){
            if (bean.getCode().equals(code)) return true;
        }
        return false;
    }

    public FBCat(Context context, String catsCodes){
        super(Cat.class,context,false);
        this.catsCodes= catsCodes;
        retrieve();
    }
    public FBCat(Context context){
        super(Cat.class,context,false);
    }



}
