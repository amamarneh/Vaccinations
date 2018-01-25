package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;

/**
 * Created by jcc on 1/10/2018.
 */

public class Stock implements Parcelable{
    public static final int TYPE_TOY=0;
    public static final int TYPE_CLOTH=1;
    public static final int TYPE_FOOD=2;

    private String code,name,desc,address,cat;
    private int type;

    public Stock() {
    }

    protected Stock(Parcel in) {
        code = in.readString();
        name = in.readString();
        desc = in.readString();
        address = in.readString();
        cat = in.readString();
        type = in.readInt();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
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
        parcel.writeString(address);
        parcel.writeString(cat);
        parcel.writeInt(type);
    }
}
