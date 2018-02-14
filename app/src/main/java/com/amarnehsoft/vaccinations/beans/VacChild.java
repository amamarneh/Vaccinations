package com.amarnehsoft.vaccinations.beans;

import java.util.Date;

/**
 * Created by alaam on 2/12/2018.
 */

public class VacChild {
    private int id;
    private String vacCode,childCode,desc;
    private int type, manualSet;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getManualSet() {
        return manualSet;
    }

    public void setManualSet(int manualSet) {
        this.manualSet = manualSet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacCode() {
        return vacCode;
    }

    public void setVacCode(String vacCode) {
        this.vacCode = vacCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public VacChild() {

    }
}
