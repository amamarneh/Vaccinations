package com.amarnehsoft.vaccinations.database.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.controllers.NotifyVaccinationsController;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.StringsUtils;

/**
 * Created by jcc on 11/12/2017.
 */

public class FBVacinations extends FirebaseHelper{
    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "vacinations";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Object bean,String query) {
        try {
            Vaccination info = (Vaccination) bean;
            return StringsUtils.like(info.getName(),"%"+query+"%");
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not vacination class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public FBVacinations(Context context,boolean retrive){
        super(Vaccination.class,context,retrive);
    }

    @Override
    protected boolean addBeanToList(Object bean) {
        try {
            Vaccination info = (Vaccination) bean;
            return  true;
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not vacination class ,, " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void afterChildAdded(Object bean,String code) {
        super.afterChildAdded(bean,code);
        try {
            Vaccination info = (Vaccination) bean;
            Vaccination saved = VacinationDB.getInstance(mContext).getBeanById(info.getCode());
            if (saved != null){
                if (saved.getManuallySet() == 0){
                    NotifyVaccinationsController.notify(mContext,info);
                }else {
                    //dont notify.
                    info.setNewAge(saved.getNewAge());
                    info.setManuallySet(saved.getManuallySet());
                    info.setNotificationId(saved.getNotificationId());
                }
            }
            VacinationDB.getInstance(mContext).saveBean(info);
            Log.e("Amarneh","vacination beas saved , name="+info.getName());
        }catch (ClassCastException e){
            Log.e("Amarneh","this is not vacination class ,, " + e.getMessage());
            e.printStackTrace();
        }
    }
}
