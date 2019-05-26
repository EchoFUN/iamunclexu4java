package com.iamunclexu.utils;

public class Utils {

    public static boolean isStaticUri(String uri) {
        if (uri.contains("/static/")) {
            return true;
        }
        return false;
    }
}
