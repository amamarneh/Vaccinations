package com.amarnehsoft.vaccinations.controllers;

import android.content.Context;
import android.util.Log;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.VacChild;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild;
import com.amarnehsoft.vaccinations.database.db2.DBVaccination;
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB;
import com.amarnehsoft.vaccinations.database.sqlite.DBVacChild;
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB;
import com.amarnehsoft.vaccinations.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
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

    public void addVaccinations(){

        List<Child> children = ChildDB.getInstance(mContext).getAll();

        AlarmsController.deleteAllAlarms(mContext);
        DBVacChild.getInstance(mContext).deleteBeansNotManuallySet();

        for (Child c : children) {
            int age = DateUtils.getDiffDays(new Date(), c.getBirthDate());
            List<Vaccination> vaccinations = DBVaccination.getInstance(mContext).getUpCommingVaccinationsForAge(age,2);
            for (Vaccination v : vaccinations) {
                VacChild vacChild = new VacChild();

                int days = v.getAge();
//                Date date = new Date(c.getBirthDate().getTime());
//
//                date.setTime(date.getTime() + days * 24 * 3600 * 1000);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(c.getBirthDate().getTime());
                calendar.add(Calendar.DAY_OF_MONTH,days);
                Date date = calendar.getTime();

                Log.d("tag","getBirthDate, date="+c.getBirthDate());
                Log.d("tag","days="+days+" , date="+date);
                vacChild.setDate(date); // notify date
                vacChild.setChildCode(c.getCode());
                vacChild.setVacCode(v.getCode());
//                vacChild.setManualSet(0); // default

                DBVacChild.getInstance(mContext).addVacChild(vacChild);
//                AlarmsController.addFixedAlarm(mContext, vacChild);

            }
        }
    }
//    public void deleteLeftVaccinations(){
//        List<VacChild> vacChildren = DBVacChild.getInstance(mContext).getAll();
//        for(VacChild vacChild: vacChildren){
//            Vaccination v = DBVaccination.getInstance(mContext).getBeanById(vacChild.getVacCode());
//            if(v == null)
//                v = VacinationDB.getInstance(mContext).getBeanById(vacChild.getVacCode());
//
//            Child c =  ChildDB.getInstance(mContext).getBeanById(vacChild.getChildCode());
//
//            if(c != null && v != null){
//                if(v.g)
//
//            }
//        }
//    }
    public List<VacinationForChild> getNotifications(){

        addVaccinations();

        List<VacinationForChild> result = new ArrayList<>();

        List<VacChild> vacChildren = DBVacChild.getInstance(mContext).getAll();
            for(VacChild vacChild: vacChildren){
                Vaccination v = DBVaccination.getInstance(mContext).getBeanById(vacChild.getVacCode());
                if(v == null)
                    v = VacinationDB.getInstance(mContext).getBeanById(vacChild.getVacCode());

                Child c =  ChildDB.getInstance(mContext).getBeanById(vacChild.getChildCode());

                if(c != null && v != null){

                VacinationForChild b = new VacinationForChild();
                b.setChild(c);
                b.setVaccination(v);
                b.setDate(vacChild.getDate());
                result.add(b);
                }
            }



        AlarmsController.notifyAllVaccinations(mContext);
        return result;
    }

    public  List<VacinationForChild> getVaccinationForChild(Child c){
        List<VacinationForChild> result= new ArrayList<>();
        List<VacChild> vacChildren = DBVacChild.getInstance(mContext).getAllByChild(c.getCode());
        for(VacChild vacChild: vacChildren){
            Vaccination v = DBVaccination.getInstance(mContext).getBeanById(vacChild.getVacCode());
            if(v == null)
                v = VacinationDB.getInstance(mContext).getBeanById(vacChild.getVacCode());
            if(v != null) {
                VacinationForChild b = new VacinationForChild();
                b.setChild(c);
                b.setVaccination(v);
                b.setDate(vacChild.getDate());
                result.add(b);
            }
        }
        return result;
    }
}
