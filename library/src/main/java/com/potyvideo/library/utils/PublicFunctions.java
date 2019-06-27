package com.potyvideo.library.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class PublicFunctions {

    public static int convertDpToPixel(Context context, float dp) {
        return (int) (dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertPixelsToDp(Context context, float px) {
        return (int) (px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
