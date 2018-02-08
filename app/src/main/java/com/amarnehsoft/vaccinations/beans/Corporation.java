package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcc on 1/24/2018.
 */

public class Corporation implements Parcelable{
    private String code,name,address,contact,desc,img,cats, catCodes;

    public Corporation() {
    }

    protected Corporation(Parcel in) {
        code = in.readString();
        name = in.readString();
        address = in.readString();
        contact = in.readString();
        desc=in.readString();
        img=in.readString();
        cats=in.readString();
        catCodes =in.readString();
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCats() {
        return cats;
    }

    public void setCats(String cats) {
        this.cats = cats;
    }

    public String getCatCodes() {
        return catCodes;
    }

    public void setCatCodes(String catCodes) {
        this.catCodes = catCodes;
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
        dest.writeString(contact);
        dest.writeString(desc);
        dest.writeString(img);
        dest.writeString(cats);
        dest.writeString(catCodes);
    }




    public static String[] getCatsFromString(String catsCodes){
        return catsCodes.split(",");
    }
}
