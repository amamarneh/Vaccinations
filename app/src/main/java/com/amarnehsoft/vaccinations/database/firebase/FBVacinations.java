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

public class FBVacinations extends FirebaseHelper<Vaccination>{
    @Override
    protected String getRefName(int level) {
        switch (level){
            case 0:
                return "vacinations";
        }
        return null;
    }

    @Override
    protected boolean addBeanToSearchList(Vaccination bean,String query) {
        return StringsUtils.like(bean.getName(),"%"+query+"%");
    }

    public FBVacinations(Context context,boolean retrive){
        super(Vaccination.class,context,retrive);
    }

    @Override
    protected boolean addBeanToList(Vaccination bean) {
        return true;
    }

    @Override
    public void afterChildAdded(Vaccination bean,String code) {
        super.afterChildAdded(bean,code);

            Vaccination saved = VacinationDB.getInstance(mContext).getBeanById(bean.getCode());
            if (saved != null){
                if (saved.getManuallySet() == 0){
                    NotifyVaccinationsController.notify(mContext,bean);
                }else {
                    //dont notify.
                    bean.setNewAge(saved.getNewAge());
                    bean.setManuallySet(saved.getManuallySet());
                    bean.setNotificationId(saved.getNotificationId());
                }
            }
            VacinationDB.getInstance(mContext).saveBean(bean);
            Log.e("Amarneh","vacination bean saved , name="+bean.getName());
    }
}
