package com.personal.capital.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patel on 6/3/2017.
 */

public class Feed implements Parcelable{
    private String title;
    private List<Article> articles;

    public Feed() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        if(articles == null) articles = new ArrayList<>();

        articles.add(article);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getTitle());

        List<Article> articles = getArticles();
        if(articles == null) {
            parcel.writeInt(0);
        }
        else {
            parcel.writeInt(1);
            parcel.writeList(articles);
        }
    }

    private Feed(Parcel in) {
        setTitle(in.readString());

        int hasArticles = in.readInt();

        if(hasArticles == 1) {
            articles = new ArrayList<>();
            in.readList(articles, Article.class.getClassLoader());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Feed> CREATOR
            = new Parcelable.Creator<Feed>() {

        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
