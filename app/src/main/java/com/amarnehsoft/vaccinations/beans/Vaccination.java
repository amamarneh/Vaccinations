package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jcc on 12/27/2017.
 */

public class Vaccination implements Parcelable{
    public static int TYPE_VACCINATION=0;
    public static int TYPE_DATE=1;

    private String code,name,desc;
    private int age;//in day
    private int manuallySet; //0 or 1
    private int newAge;
    private int notificationId;
    private int type;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    public Vaccination(){date = new Date();}

    protected Vaccination(Parcel in) {
        code = in.readString();
        name = in.readString();
        desc = in.readString();
        age = in.readInt();
        newAge = in.readInt();
        manuallySet = in.readInt();
        notificationId = in.readInt();
        type = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<Vaccination> CREATOR = new Creator<Vaccination>() {
        @Override
        public Vaccination createFromParcel(Parcel in) {
            return new Vaccination(in);
        }

        @Override
        public Vaccination[] newArray(int size) {
            return new Vaccination[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getManuallySet() {
        return manuallySet;
    }

    public void setManuallySet(int manuallySet) {
        this.manuallySet = manuallySet;
    }

    public int getNewAge() {
        return newAge;
    }

    public void setNewAge(int newAge) {
        this.newAge = newAge;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeInt(age);
        parcel.writeInt(newAge);
        parcel.writeInt(manuallySet);
        parcel.writeInt(notificationId);
        parcel.writeInt(type);
        parcel.writeLong(date.getTime());
    }
}
