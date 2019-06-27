package com.iamunclexu.utils;

public class ArrayUtils {

    public static boolean contains(String[] original, String target) {
        for (int i = 0; i < original.length; i++) {
            if (original[i].equals(target)) {
                return true;
            }
        }
        return false;
    }
}
