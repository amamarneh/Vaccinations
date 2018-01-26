package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcc on 1/10/2018.
 */

public class Kindergarten implements Parcelable{
    private String code,name, address,description,fromTime,toTime,imgUrl, contactInfo;
    private int fromYear,toYear,fromDay,toDay;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Kindergarten() {
    }

    protected Kindergarten(Parcel in) {
        code = in.readString();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        fromTime = in.readString();
        toTime = in.readString();
        fromYear = in.readInt();
        toYear = in.readInt();
        fromDay = in.readInt();
        toDay = in.readInt();
        imgUrl = in.readString();
        contactInfo = in.readString();
    }

    public static final Creator<Kindergarten> CREATOR = new Creator<Kindergarten>() {
        @Override
        public Kindergarten createFromParcel(Parcel in) {
            return new Kindergarten(in);
        }

        @Override
        public Kindergarten[] newArray(int size) {
            return new Kindergarten[size];
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public int getFromDay() {
        return fromDay;
    }

    public void setFromDay(int fromDay) {
        this.fromDay = fromDay;
    }

    public int getToDay() {
        return toDay;
    }

    public void setToDay(int toDay) {
        this.toDay = toDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(description);
        parcel.writeString(fromTime);
        parcel.writeString(toTime);
        parcel.writeInt(fromYear);
        parcel.writeInt(toYear);
        parcel.writeInt(fromDay);
        parcel.writeInt(toDay);
        parcel.writeString(imgUrl);
        parcel.writeString(contactInfo);
    }
}
