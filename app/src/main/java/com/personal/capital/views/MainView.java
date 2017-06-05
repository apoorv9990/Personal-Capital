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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.adapters.ArticleAdapter;
import com.personal.capital.models.Article;
import com.personal.capital.utils.PixelUtil;

import java.util.List;

/**
 * Created by patel on 6/1/2017.
 *
 * Used in the {@link com.personal.capital.activities.MainActivity}
 */

public class MainView extends LinearLayout {

    private FrameLayout mHeader;
    
    private ImageView mRefreshImageView;
    
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

//        setUpTitleView();
        setUpHeaderView();
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

    public void setOnRefreshClickListener(OnClickListener listener) {
        mRefreshImageView.setOnClickListener(listener);
    }
    
    private void setUpHeaderView() {
        mHeader = new FrameLayout(getContext());

        LinearLayout.LayoutParams headerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mHeader.setLayoutParams(headerParams);

        setUpTitleView();
        setUpRefreshView();

        addView(mHeader);
    }

    private void setUpTitleView() {
        mTitleTextView = new TextView(getContext());

        FrameLayout.LayoutParams titleParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mTitleTextView.setLayoutParams(titleParams);

        int spacingSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_s));
        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        mTitleTextView.setPadding(spacingSmall, spacingXSmall, spacingSmall, spacingXSmall);
        mTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.title_size));
        mTitleTextView.setGravity(Gravity.CENTER);

        mHeader.addView(mTitleTextView);
    }

    private void setUpRefreshView() {
        mRefreshImageView = new ImageView(getContext());

        int refreshDimension = getContext().getResources().getDimensionPixelSize(R.dimen.refresh_size);

        FrameLayout.LayoutParams refreshParams = new FrameLayout.LayoutParams(refreshDimension, refreshDimension);
        refreshParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        refreshParams.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.refresh_margin_right);

        mRefreshImageView.setLayoutParams(refreshParams);

        mRefreshImageView.setImageResource(R.drawable.ic_refresh);

        mHeader.addView(mRefreshImageView);
    }

    private void setUpRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());

        LinearLayout.LayoutParams recyclerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mRecyclerView.setLayoutParams(recyclerParams);

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
