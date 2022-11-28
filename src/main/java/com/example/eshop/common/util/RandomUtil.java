package com.example.eshop.common.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtil {
    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random rand = new Random();

    public String generateString(int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i=0; i<length; i++) {
            int idx = rand.nextInt(BASE.length());
            randomString.append(BASE.charAt(idx));
        }
        return randomString.toString();
    }
}
