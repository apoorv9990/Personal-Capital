package com.personal.capital.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.utils.PixelUtil;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleView extends LinearLayout {

    private ImageView articleImageView;
    private TextView articleTitleTextView;

    public ArticleView(Context context) {
        super(context);
        init();
    }

    public ArticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.rounded_article_view);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setUpImageView();
        setUpTitleView();
    }

    public void setUpImageView(){
        this.articleImageView = new ImageView(getContext());

        int imageHeight = PixelUtil.dpToPx(getContext(), 150);

        LinearLayout.LayoutParams imageViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageHeight);

        this.articleImageView.setLayoutParams(imageViewParams);

        this.articleImageView.setBackgroundResource(R.mipmap.ic_launcher_round);

        addView(this.articleImageView);
    }

    public void setUpTitleView() {
        this.articleTitleTextView = new TextView(getContext());

        LinearLayout.LayoutParams imageViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.articleTitleTextView.setLayoutParams(imageViewParams);

        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        this.articleTitleTextView.setPadding(spacingXSmall, spacingXSmall, spacingXSmall, spacingXSmall);
        this.articleTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        this.articleTitleTextView.setMaxLines(2);
        this.articleTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        this.articleTitleTextView.setText("Title");

        addView(this.articleTitleTextView);
    }
}
