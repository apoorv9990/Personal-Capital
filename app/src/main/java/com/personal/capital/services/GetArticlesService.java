package com.personal.capital.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;

import com.personal.capital.R;
import com.personal.capital.models.Feed;
import com.personal.capital.parser.FeedParser;
import com.personal.capital.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by patel on 6/1/2017.
 *
 * Used to get the articles from API and then send it to {@link com.personal.capital.receivers.ArticleResultReceiver}
 */

public class GetArticlesService extends IntentService {

    public GetArticlesService() {
        super(GetArticlesService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra(Constants.ARTICLE_RECEIVER);
        String command = intent.getStringExtra(Constants.COMMAND);

        if(command.equals(Constants.GET)) {
            receiver.send(Constants.RUNNING, Bundle.EMPTY);

            try{
                Feed feed = getArticles();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.ARTICLES_RESPONSE, feed);
                receiver.send(Constants.FINISHED, bundle);
            }
            catch (Exception e) {
                e.printStackTrace();
                receiver.send(Constants.ERROR, Bundle.EMPTY);
            }
        }
    }

    /**
     * Fetch the articles from API
     * @return the parsed Feed object
     */
    private Feed getArticles() {

        InputStream stream = null;
        HttpsURLConnection connection = null;

        Feed feed = null;

        try {

            URL articleUrl = new URL(getApplicationContext().getResources().getString(R.string.article_url));

            connection = (HttpsURLConnection) articleUrl.openConnection();
            connection.setRequestMethod(Constants.GET);

            connection.connect();

            stream = connection.getInputStream();
            if(stream != null) {
                FeedParser parser = new FeedParser();
                feed = parser.parse(stream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(connection != null) connection.disconnect();
        }

        return feed;
    }
}
