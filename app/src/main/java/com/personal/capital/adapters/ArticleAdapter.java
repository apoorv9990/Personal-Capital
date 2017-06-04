package com.personal.capital.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

    private int screenWidth = 0;

    private LruCache mMemoryCache;

    private Interactor mInteractor;

    public ArticleAdapter(Interactor interactor) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

        mInteractor = interactor;

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

        return new ArticleViewHolder(view, mInteractor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article currentArticle = mArticles.get(position);

        ((ArticleViewHolder)holder).bind(currentArticle);
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

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Interactor mInteractor;
        private Article mArticle;

        public ArticleViewHolder(ArticleView view, Interactor interactor) {
            super(view);
            mInteractor = interactor;
            view.setOnClickListener(this);
        }

        public void bind(Article article) {

            mArticle = article;

            ArticleView articleView = (ArticleView) itemView;

            if(StringUtils.isNotNullOrEmpty(mArticle.getTitle())) {
                articleView.setTitle(mArticle.getTitle());
            }

            Bitmap articleImage = getBitmapFromMemCache(mArticle.getPicture().getUrl());

            if(articleImage != null) {
                articleView.setArticleImageBitmap(articleImage);
            } else {
                articleView.showProgress();
                BitmapWorkerTask.loadBitmap(articleView.getContext(), mArticle.getPicture().getUrl(), articleView);
            }

            if(articleView instanceof MainArticleView) {

                MainArticleView mainArticleView = ((MainArticleView) articleView);

                if(StringUtils.isNotNullOrEmpty(mArticle.getDescription())) {
                    mainArticleView.setDescription(mArticle.getDescription());
                }
            }
        }

        @Override
        public void onClick(View view) {
            mInteractor.onItemClicked(mArticle);
        }
    }

    public interface Interactor {
        void onItemClicked(Article article);
    }
}