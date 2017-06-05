package com.personal.capital.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.personal.capital.R;

/**
 * Created by patel on 6/1/2017.
 *
 * Provides spacing between items in RecyclerView
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        boolean isTablet = parent.getContext().getResources().getBoolean(R.bool.isTablet);

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        int position = parent.getChildLayoutPosition(view);

        // Add top margin only for the first item to avoid double space between items
        if (position == 0)
            outRect.top = space;
        else
            outRect.top = 0;

        if(isTablet) {
            if(position % 3 != 1 && position != 0)
                outRect.left = 0;
        }
        else {
            // Add left margin only to articles on the left because they also have right margin
            if (position % 2 == 0 && position != 0)
                outRect.left = 0;
        }
    }
}
