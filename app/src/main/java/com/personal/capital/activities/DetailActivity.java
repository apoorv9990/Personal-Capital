package com.personal.capital.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.personal.capital.views.DetailView;

import java.net.URI;

/**
 * Created by patel on 6/3/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String TITLE = "title";
    private static final String URL = "url";

    private DetailView mView;

    private String articleTitle;
    private String articleLink;

    public static void startDetailActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new DetailView(this);
        setContentView(mView);

        Intent intent = getIntent();

        if(intent.hasExtra(TITLE)) {
            articleTitle = intent.getStringExtra(TITLE);
        }

        if(intent.hasExtra(URL)) {
            articleLink = intent.getStringExtra(URL);
        }

        mView.setTitle(articleTitle);
        mView.setArticleUrl(articleLink + "?displayMobileNavigation=0");
    }
}
