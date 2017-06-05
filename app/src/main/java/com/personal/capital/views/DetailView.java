package com.personal.capital.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.utils.PixelUtil;

/**
 * Created by patel on 6/3/2017.
 *
 * Shows the article link in a {@link WebView}
 */

public class DetailView extends LinearLayout {

    private TextView mTitleTextView;
    private WebView mWebView;

    public DetailView(Context context) {
        super(context);
        init();
    }

    public DetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setOrientation(VERTICAL);

        setUpTitleView();
        setUpWebView();
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setArticleUrl(String url) {
        mWebView.loadUrl(url);
    }

    private void setUpTitleView() {
        mTitleTextView = new TextView(getContext());

        LinearLayout.LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mTitleTextView.setLayoutParams(titleParams);

        int spacingSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_s));
        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        mTitleTextView.setPadding(spacingSmall, spacingXSmall, spacingSmall, spacingXSmall);
        mTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.title_size));
        mTitleTextView.setGravity(Gravity.CENTER);

        addView(mTitleTextView);
    }

    private void setUpWebView() {
        mWebView = new WebView(getContext());

        LinearLayout.LayoutParams webViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mWebView.setLayoutParams(webViewParams);

        addView(mWebView);
    }
}
