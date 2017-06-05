package com.personal.capital.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.personal.capital.models.Article;
import com.personal.capital.utils.StringUtils;
import com.personal.capital.views.ArticleView;
import com.personal.capital.views.MainArticleView;
import com.personal.capital.workers.BitmapWorkerTask;

import java.util.List;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter {

    private final int MAIN_ARTICLE = 0;
    private final int PREVIOUS_ARTICLE = 1;

    private List<Article> mArticles;

    private Interactor mInteractor;

    public ArticleAdapter(Interactor interactor) {
        mInteractor = interactor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        // return the full screen view if it is the first position
        if(position == 0) {
            return MAIN_ARTICLE;
        }

        return PREVIOUS_ARTICLE;
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
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

            // set the title if it is noot empty
            if(StringUtils.isNotNullOrEmpty(mArticle.getTitle())) {
                articleView.setTitle(mArticle.getTitle());
            }

            // load the Bitmap
            articleView.showProgress();
            BitmapWorkerTask.loadBitmap(articleView.getContext(), mArticle.getPicture().getUrl(), articleView);

            // if view is full screen view load the description as well
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

    // Used to pass the interactions to the Activity
    public interface Interactor {
        void onItemClicked(Article article);
    }
}