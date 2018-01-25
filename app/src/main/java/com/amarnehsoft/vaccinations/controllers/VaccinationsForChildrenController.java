package com.amarnehsoft.vaccinations.controllers;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jcc on 12/29/2017.
 */

public class VaccinationsForChildrenController {

    private Context mContext;
    public VaccinationsForChildrenController(Context context){
        mContext = context;
    }

    public List<VacinationForChild> getNotifications(){
        //List<Vaccination> vaccinations = VacinationDB.getInstance(mContext).getAll();
        Log.e("Amarneh","get notifications");
        List<VacinationForChild> result = new ArrayList<>();
        List<Child> children = ChildDB.getInstance(mContext).getAll();

        for (Child c : children){
            int age = DateUtils.getDiffDays(new Date(),c.getBirthDate());
            List<Vaccination> vaccinations = VacinationDB.getInstance(mContext).getUpCommingVaccinationsForAge(age,2);
            for (Vaccination v : vaccinations){
                VacinationForChild b = new VacinationForChild();
                b.setChild(c);
                b.setVaccination(v);
                result.add(b);
            }
        }

        return result;
    }
}
