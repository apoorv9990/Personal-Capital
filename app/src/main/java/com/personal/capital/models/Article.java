package com.personal.capital.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patel on 6/3/2017.
 */

public class Article implements Parcelable{
    private String title;
    private String description;
    private String link;

    private Picture picture;

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getTitle());
        parcel.writeString(getDescription());
        parcel.writeString(getLink());

        parcel.writeParcelable(picture, 0);
    }

    private Article(Parcel in) {
        setTitle(in.readString());
        setDescription(in.readString());
        setLink(in.readString());

        setPicture((Picture) in.readParcelable(Picture.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Article> CREATOR
            = new Parcelable.Creator<Article>() {

        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
