package com.personal.capital.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal.capital.adapters.ArticleAdapter;
import com.personal.capital.models.Article;
import com.personal.capital.models.Feed;
import com.personal.capital.receivers.ArticleResultReceiver;
import com.personal.capital.services.GetArticlesService;
import com.personal.capital.utils.Constants;
import com.personal.capital.views.MainView;

public class MainActivity extends AppCompatActivity implements ArticleResultReceiver.Receiver{

    private static final String FEED = "feed";

    private ArticleResultReceiver mReceiver;

    private ProgressDialog mProgressDialog;

    private MainView mView;

    private Feed mFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new MainView(this);
        setContentView(mView);

        ArticleAdapter articleAdapter = new ArticleAdapter(new ArticleAdapter.Interactor() {
            @Override
            public void onItemClicked(Article article) {
                DetailActivity.startDetailActivity(MainActivity.this, article.getTitle(), article.getLink());
            }
        });

        mView.setAdapter(articleAdapter);
        mView.setOnRefreshClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArticles();
            }
        });

        mReceiver = new ArticleResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);

        if(savedInstanceState != null) {
            mFeed = savedInstanceState.getParcelable(FEED);
        }

        if(mFeed == null) {
            getArticles();
        } else {
            mView.setTitle(mFeed.getTitle());
            mView.setArticles(mFeed.getArticles());
        }
    }

    @Override
    protected void onDestroy() {
        mReceiver.setReceiver(null);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FEED, mFeed);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case Constants.RUNNING:
                //show progress
                mProgressDialog.setTitle("Fetching articles...");
                mProgressDialog.show();
                break;
            case Constants.FINISHED:
                mFeed = resultData.getParcelable(Constants.ARTICLES_RESPONSE);

                mView.setTitle(mFeed.getTitle());
                mView.setArticles(mFeed.getArticles());

                mProgressDialog.hide();
                // do something interesting
                // hide progress
                break;
            case Constants.ERROR:
                mProgressDialog.hide();
                Toast.makeText(this, "Oops", Toast.LENGTH_SHORT).show();
                // handle the error;
                break;
        }
    }

    public void getArticles() {
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, GetArticlesService.class);
        intent.putExtra(Constants.ARTICLE_RECEIVER, mReceiver);
        intent.putExtra(Constants.COMMAND, Constants.GET);
        startService(intent);
    }
}
