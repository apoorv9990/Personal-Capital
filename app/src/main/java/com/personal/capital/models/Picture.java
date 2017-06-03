package com.personal.capital.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patel on 6/3/2017.
 */

public class Picture implements Parcelable{
    private int width;
    private int height;

    private String url;

    public Picture() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getWidth());
        parcel.writeInt(getHeight());

        parcel.writeString(getUrl());
    }

    private Picture(Parcel in) {
        setWidth(in.readInt());
        setHeight(in.readInt());

        setUrl(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Picture> CREATOR
            = new Parcelable.Creator<Picture>() {

        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
