package com.personal.capital.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by patel on 6/1/2017.
 */

public class PixelUtil {
    // Converts given dp and return pixels
    public static int dpToPx(Context context, int dp) {
        int px = Math.round(dp * getPixelScaleFactor(context));
        return px;
    }

    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
