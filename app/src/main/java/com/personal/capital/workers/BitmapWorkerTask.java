package com.personal.capital.workers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.personal.capital.models.Picture;
import com.personal.capital.views.ArticleView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by patel on 6/3/2017.
 */

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    // LruCache used to maintain loaded Bitmaps
    private static LruCache<String, Bitmap> cache = null;

    // Used to maintain a WeakReference to the row
    private final WeakReference<ArticleView> weakReference;
    private String url = "";

    public BitmapWorkerTask(ArticleView articleView) {
        // initialize the cache if null
        if (cache == null) {
            final int memClass = ((ActivityManager) articleView.getContext().getSystemService(
                    Context.ACTIVITY_SERVICE)).getMemoryClass();
            cache = new LruCache<String, Bitmap>(1024 * 1024 * memClass / 3) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        weakReference = new WeakReference<>(articleView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            url = strings[0];
            Bitmap bitmap = getBitmapFromCache(url);

            // check if bitmap exists in cache
            if (bitmap != null) {
                return bitmap;
            }

            // download the Bitmap if it does not exist
            bitmap = BitmapFactory.decodeStream(new URL(url).openStream());

            // add downloaded Bitmap to cache
            addBitmapToCache(url, bitmap);
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }

    // Return bitmap from cache, if it exists, or null
    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = cache.get(key);
        return bitmap;
    }

    // Add Bitmap to cache if it does not exist
    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (cache.get(key) == null) {
            cache.put(key, bitmap);
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        // if the view is still around load the image and hide progress bar
        if (weakReference != null && bitmap != null) {
            ArticleView articleView = weakReference.get();
            final BitmapWorkerTask bitmapWorkerTask =
                    getBitmapWorkerTask(articleView);
            if (this == bitmapWorkerTask && articleView != null) {
                articleView.setArticleImageBitmap(bitmap);
                articleView.hideProgress();
            }
        }
    }

    // Returns a BitmapWorkerTask associated with the ArticleView
    private static BitmapWorkerTask getBitmapWorkerTask(ArticleView articleView) {
        if (articleView != null) {
            final Drawable drawable = articleView.getArticleImageDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    // Cancels the previous BitmapWorkerTask associated with ArticleView, if any
    public static boolean cancelPotentialWork(String url, ArticleView articleView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(articleView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.url;
            if (!bitmapData.equals(url)) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    // Used to load a Bitmap into ArticleView
    public static void loadBitmap(Context context, String url, ArticleView articleView) {
        if (cancelPotentialWork(url, articleView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(articleView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(context.getResources(), null, task);
            articleView.setArticleImageDrawable(asyncDrawable);
            task.execute(url);
        }
    }


    public static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
}