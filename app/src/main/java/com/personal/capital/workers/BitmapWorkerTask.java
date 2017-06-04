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

    private static LruCache<String, Bitmap> cache = null;

    private final WeakReference<ArticleView> weakReference;
    private String url = "";

    public BitmapWorkerTask(ArticleView articleView) {
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

            if (bitmap != null) {
                return bitmap;
            }

            Rect outPadding = new Rect(-1, -1, -1, -1);

            bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
            addBitmapToCache(url, bitmap);
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = cache.get(key);
        return bitmap;
    }

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