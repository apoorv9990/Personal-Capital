package com.personal.capital.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personal.capital.R;
import com.personal.capital.adapters.ArticleAdapter;
import com.personal.capital.utils.PixelUtil;

/**
 * Created by patel on 6/1/2017.
 */

public class MainView extends LinearLayout {

    private TextView titleTextView;

    private RecyclerView recyclerView;

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

    public void setUpTitleView() {
        this.titleTextView = new TextView(getContext());

        LinearLayout.LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int spacingSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_s));
        int spacingXSmall = PixelUtil.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.spacing_xs));

        this.titleTextView.setPadding(spacingSmall, spacingXSmall, spacingSmall, spacingXSmall);
        this.titleTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
//        this.titleTextView.setTextSize(PixelUtil.dpToPx(getContext(), 8));
        this.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23);
        this.titleTextView.setGravity(Gravity.CENTER);
        this.titleTextView.setText("Title");

        this.titleTextView.setLayoutParams(titleParams);

        addView(titleTextView);
    }

    public void setUpRecyclerView() {
        this.recyclerView = new RecyclerView(getContext());

        LinearLayout.LayoutParams recyclerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.recyclerView.setLayoutParams(recyclerParams);

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
        recyclerView.setLayoutManager(layoutManager);

        ArticleAdapter adapter = new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        addView(recyclerView);
    }
}
