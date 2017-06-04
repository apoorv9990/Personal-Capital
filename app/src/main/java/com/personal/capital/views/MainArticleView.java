package com.personal.capital.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.models.Article;
import com.personal.capital.utils.PixelUtil;

/**
 * Created by patel on 6/3/2017.
 */

public class MainArticleView extends ArticleView{

    private TextView mArticleDescriptionTextView;

    public MainArticleView(Context context) {
        super(context);
    }

    public MainArticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainArticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainArticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();

        setTitleMaxLines(1);
        setUpDescriptionView();
    }

    public void setDescription(String description) {
        Spanned titleSpanned;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            titleSpanned = Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY);
        } else {
            titleSpanned = Html.fromHtml(description);
        }

        this.mArticleDescriptionTextView.setText(titleSpanned);
    }

    private void setUpDescriptionView() {
        this.mArticleDescriptionTextView = new TextView(getContext());

        LinearLayout.LayoutParams imageViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mArticleDescriptionTextView.setLayoutParams(imageViewParams);

        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        this.mArticleDescriptionTextView.setPadding(spacingXSmall, 0, spacingXSmall, spacingXSmall);
        this.mArticleDescriptionTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        this.mArticleDescriptionTextView.setMaxLines(2);
        this.mArticleDescriptionTextView.setEllipsize(TextUtils.TruncateAt.END);
        this.mArticleDescriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimensionPixelSize(R.dimen.article_description_size));

        addView(this.mArticleDescriptionTextView);
    }
}
