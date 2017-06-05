package com.personal.capital.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public ArticleResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.mReceiver = receiver;
    }

    /**
     * Passed the received result from {@link com.personal.capital.services.GetArticlesService}
     * to {@link com.personal.capital.activities.MainActivity}
     */
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

    // Used to pass results to the @MainActivity
    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
