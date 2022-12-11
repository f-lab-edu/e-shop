package com.example.eshop.common.util;

import java.util.Random;

public class RandomUtil {
    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static Random rand;

    public static String generateString(int length) {
        rand = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i=0; i<length; i++) {
            int idx = rand.nextInt(BASE.length());
            randomString.append(BASE.charAt(idx));
        }
        return randomString.toString();
    }
}
