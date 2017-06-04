package com.personal.capital.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.adapters.ArticleAdapter;
import com.personal.capital.models.Article;
import com.personal.capital.utils.PixelUtil;

import java.util.List;

/**
 * Created by patel on 6/1/2017.
 */

public class MainView extends LinearLayout {

    private TextView mTitleTextView;

    private RecyclerView mRecyclerView;

    private ArticleAdapter mAdapter;

    public MainView(Context context) {
        super(context);
        init();
    }

    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {

        setOrientation(VERTICAL);

        setUpTitleView();
        setUpRecyclerView();
    }
    
    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setAdapter(ArticleAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setArticles(List<Article> articles) {
        mAdapter.setArticles(articles);
    }

    private void setUpTitleView() {
        this.mTitleTextView = new TextView(getContext());

        LinearLayout.LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mTitleTextView.setLayoutParams(titleParams);

        int spacingSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_s));
        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        this.mTitleTextView.setPadding(spacingSmall, spacingXSmall, spacingSmall, spacingXSmall);
        this.mTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        this.mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.title_size));
        this.mTitleTextView.setGravity(Gravity.CENTER);

        addView(mTitleTextView);
    }

    private void setUpRecyclerView() {
        this.mRecyclerView = new RecyclerView(getContext());

        LinearLayout.LayoutParams recyclerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mRecyclerView.setLayoutParams(recyclerParams);

        int spanCount = 2;

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet)
            spanCount = 3;

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? layoutManager.getSpanCount() : 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        addView(mRecyclerView);
    }
}
