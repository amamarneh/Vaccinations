package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcc on 1/24/2018.
 */

public class Corporation implements Parcelable{
    private String code,name,address;

    public Corporation() {
    }

    protected Corporation(Parcel in) {
        code = in.readString();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<Corporation> CREATOR = new Creator<Corporation>() {
        @Override
        public Corporation createFromParcel(Parcel in) {
            return new Corporation(in);
        }

        @Override
        public Corporation[] newArray(int size) {
            return new Corporation[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(address);
    }
}
