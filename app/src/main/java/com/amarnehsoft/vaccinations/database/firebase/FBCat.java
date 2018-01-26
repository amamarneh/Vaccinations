package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBCat extends FirebaseHelper<Cat>{
    private String  corporationCode;

    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "cats";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Cat bean,String query) {
        try {
            return StringsUtils.like(bean.getName(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not ad class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean addBeanToList(Cat bean) {
        Log.e("Amarneh","FBCat.addBeanToList,name="+bean.getName()+",corporationCode="+corporationCode);
        return (bean.getCorporationCode().equals(corporationCode));
    }

    @Override
    protected Query orderBy(DatabaseReference db) {
//        return super.orderBy(db);
        Log.e("Amarneh","FBCat.orderBy,corporationCode="+corporationCode);
        return db.orderByChild("corporationCode").equalTo(corporationCode);
    }

    public FBCat(Context context, String corporationCode){
        super(Cat.class,context,false);
        this.corporationCode = corporationCode;
        retrieve();
    }
}
