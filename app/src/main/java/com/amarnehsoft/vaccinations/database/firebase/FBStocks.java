package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBStocks extends FirebaseHelper<Stock>{
    private String catCode,corporationCode;

    public FBStocks(Context context,String catCode,String corporationCode){
        super(Stock.class,context,false);
        this.catCode = catCode;
        this.corporationCode =  corporationCode;
        retrieve();
    }

    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "stocks";
        }
        return null;
    }

    public static DatabaseReference getDataRef(){
        return FirebaseDatabase.getInstance().getReference().child("stocks");
    }

    @Override
    protected boolean addBeanToSearchList(Stock bean,String query) {
        return StringsUtils.like(bean.getName(),"%"+query+"%");
    }

    @Override
    protected boolean addBeanToList(Stock bean) {
        return bean.getCatCode().equals(catCode);
    }

    @Override
    protected Query orderBy(DatabaseReference db) {
        return db.orderByChild("corporationCode").equalTo(corporationCode);
    }
}
