package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBStocks extends FirebaseHelper<Stock>{
    private String catCode;

    public FBStocks(Context context,String catCode){
        super(Stock.class,context,false);
        this.catCode = catCode;
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

    @Override
    protected boolean addBeanToSearchList(Stock bean,String query) {
        return StringsUtils.like(bean.getName(),"%"+query+"%");
    }

    @Override
    protected boolean addBeanToList(Stock bean) {
        return true;
    }

    @Override
    protected Query orderBy(DatabaseReference db) {
        return db.orderByChild("catCode").equalTo(catCode);
    }
}
