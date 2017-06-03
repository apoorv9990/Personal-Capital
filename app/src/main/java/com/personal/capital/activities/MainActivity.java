package com.personal.capital.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.personal.capital.receivers.ArticleResultReceiver;
import com.personal.capital.services.GetArticlesService;
import com.personal.capital.utils.Constants;
import com.personal.capital.views.MainView;

public class MainActivity extends AppCompatActivity implements ArticleResultReceiver.Receiver{

    private ArticleResultReceiver mReceiver;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainView view = new MainView(this);
        setContentView(view);

        mReceiver = new ArticleResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);

        getArticles();
    }

    @Override
    protected void onDestroy() {
        mReceiver.setReceiver(null);
        super.onDestroy();
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
                String resultString = resultData.getString(Constants.ARTICLES_RESPONSE);
                System.err.println("articleResponse " + resultString);
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