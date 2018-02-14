package com.amarnehsoft.vaccinations.beans.custome;

import android.os.Parcel;
import android.os.Parcelable;

import com.amarnehsoft.vaccinations.beans.Child;
import com.amarnehsoft.vaccinations.beans.Vaccination;

import java.util.Date;

/**
 * Created by jcc on 12/29/2017.
 */

public class VacinationForChild {
    private Child child;
    private Vaccination vaccination;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public VacinationForChild() {

    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }
}
