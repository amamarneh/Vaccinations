package com.amarnehsoft.vaccinations.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcc on 12/31/2017.
 */

public class Ad implements Parcelable{
    private String code,content,img;

    public Ad(){}

    protected Ad(Parcel in) {
        code = in.readString();
        content = in.readString();
        img = in.readString();
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(content);
        parcel.writeString(img);
    }
}
