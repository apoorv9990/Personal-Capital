package com.personal.capital.utils;

/**
 * Created by patel on 6/3/2017.
 */

public class StringUtils {
    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
