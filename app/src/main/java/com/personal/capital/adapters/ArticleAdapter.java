package com.personal.capital.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.personal.capital.R;
import com.personal.capital.models.Article;
import com.personal.capital.utils.StringUtils;
import com.personal.capital.views.ArticleView;
import com.personal.capital.views.MainArticleView;
import com.personal.capital.workers.BitmapWorkerTask;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter {

    private final int MAIN_ARTICLE = 0;
    private final int PREVIOUS_ARTICLE = 1;

    private List<Article> mArticles;

    private int spanCount;
    private int screenWidth = 0;

    private LruCache mMemoryCache;

    public ArticleAdapter() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(screenWidth == 0)
            screenWidth = parent.getContext().getResources().getDisplayMetrics().widthPixels;

        ArticleView view;

        if(viewType == MAIN_ARTICLE) {
            view = new MainArticleView(parent.getContext());
        } else {
            view = new ArticleView(parent.getContext());
        }

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article currentArticle = mArticles.get(position);

        ArticleView articleView = ((ArticleView) holder.itemView);
        articleView.setTag(position);

        if(StringUtils.isNotNullOrEmpty(currentArticle.getTitle())) {
            articleView.setTitle(currentArticle.getTitle());
        }

        Bitmap articleImage = getBitmapFromMemCache(currentArticle.getPicture().getUrl());

        if(articleImage != null) {
            articleView.setArticleImageBitmap(articleImage);
        } else {
            articleView.showProgress();
            BitmapWorkerTask.loadBitmap(holder.itemView.getContext(), currentArticle.getPicture().getUrl(), articleView);
        }

        int imageWidth = screenWidth / 3;

        if(articleView instanceof MainArticleView) {

            imageWidth = screenWidth;

            MainArticleView mainArticleView = ((MainArticleView) articleView);

            if(StringUtils.isNotNullOrEmpty(currentArticle.getDescription())) {
                mainArticleView.setDescription(currentArticle.getDescription());
            }
        }

        int imageHeight = (imageWidth * currentArticle.getPicture().getHeight()) / currentArticle.getPicture().getWidth();

        System.err.println("imageWidth: " + imageWidth + " imageHeight: " + imageHeight);
    }

    @Override
    public int getItemCount() {

        if(this.mArticles == null) return 0;

        return this.mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0) {
            return MAIN_ARTICLE;
        }

        return PREVIOUS_ARTICLE;
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleViewHolder(ArticleView view) {
            super(view);
        }
    }
}