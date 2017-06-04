package com.personal.capital.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.utils.PixelUtil;

/**
 * Created by patel on 6/1/2017.
 */

public class ArticleView extends LinearLayout {

    private FrameLayout mImageViewContainer;
    private ProgressBar mImageViewProgressDialog;
    private ImageView mArticleImageView;
    private TextView mArticleTitleTextView;

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
    
    public void setTitle(String title) {
        Spanned titleSpanned;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            titleSpanned = Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY);
        } else {
            titleSpanned = Html.fromHtml(title);
        }

        this.mArticleTitleTextView.setText(titleSpanned);
    }

    public void setTitleMaxLines(int maxLines) {
        mArticleTitleTextView.setMaxLines(maxLines);
    }

    public void setArticleImageBitmap(Bitmap bitmap) {
        mArticleImageView.setImageBitmap(bitmap);
    }

    public void setArticleImageDrawable(Drawable drawable) {
        mArticleImageView.setImageDrawable(drawable);
    }

    public Drawable getArticleImageDrawable() {
        return mArticleImageView.getDrawable();
    }

    public void hideProgress() {
        mImageViewProgressDialog.setVisibility(View.GONE);
    }

    public void showProgress() {
        mImageViewProgressDialog.setVisibility(View.VISIBLE);
    }

    protected void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.rounded_article_view);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        setUpImageView();
        setUpImageContainer();
        setUpTitleView();
    }

    private void setUpImageContainer() {
        mImageViewContainer = new FrameLayout(getContext());

        LinearLayout.LayoutParams imageViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mImageViewContainer.setLayoutParams(imageViewParams);

        setUpImageView();
        setUpImageViewProgressDialog();

        addView(mImageViewContainer);
    }

    private void setUpImageViewProgressDialog() {
        mImageViewProgressDialog = new ProgressBar(getContext());

        FrameLayout.LayoutParams imageViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mImageViewProgressDialog.setLayoutParams(imageViewParams);

        mImageViewProgressDialog.setIndeterminate(true);

        mImageViewContainer.addView(mImageViewProgressDialog);
    }

    private void setUpImageView(){
        mArticleImageView = new ImageView(getContext());

        int imageHeight = PixelUtil.dpToPx(getContext(), 150);

        FrameLayout.LayoutParams imageViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageHeight);

        mArticleImageView.setLayoutParams(imageViewParams);

        mArticleImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImageViewContainer.addView(mArticleImageView);
    }

    private void setUpTitleView() {
        mArticleTitleTextView = new TextView(getContext());

        LinearLayout.LayoutParams titleViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mArticleTitleTextView.setLayoutParams(titleViewParams);

        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        mArticleTitleTextView.setPadding(spacingXSmall, spacingXSmall, spacingXSmall, spacingXSmall);
        mArticleTitleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        mArticleTitleTextView.setMaxLines(2);
        mArticleTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        mArticleTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.article_title_size));

        addView(mArticleTitleTextView);
    }
}
