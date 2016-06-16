package com.globant.scriptsapadea.utils;

/**
 * @author nicolas.quartieri.
 */
public class StringIntUtil {

    public static boolean isInt(String value) {
        boolean result;
        try {
            Integer.parseInt(value);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }

}
