package com.personal.capital.utils;

/**
 * Created by patel on 6/3/2017.
 */

public class StringUtils {

    /**
     * @param s - string to check
     * @return boolean whether String is empty or not
     */
    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

}
