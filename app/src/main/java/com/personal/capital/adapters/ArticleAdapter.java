package com.personal.capital.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.personal.capital.views.ArticleView;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter {

    private final int MAIN_ARTICLE = 0;
    private final int PREVIOUS_ARTICLE = 1;

    public ArticleAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ArticleView view = new ArticleView(parent.getContext());

//        if(viewType == MAIN_ARTICLE) {
//            view.setLayoutParams(new ViewGroup.LayoutParams(parent.getContext().getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT));
//        }
//        else {
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        }

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0)
            return MAIN_ARTICLE;

        return PREVIOUS_ARTICLE;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleViewHolder(ArticleView view) {
            super(view);
        }
    }
}
