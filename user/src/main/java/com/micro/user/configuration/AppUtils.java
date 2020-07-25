package com.micro.user.configuration;

import org.apache.commons.lang3.RandomStringUtils;

public class AppUtils {

    private static String numbers = "1234567890";
    private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String text = chars + numbers;

    public static String generateOTP(int length) {
        return RandomStringUtils.random(length, numbers);
    }

    public static String generateToken(int length) {
        return RandomStringUtils.random(length, text);
    }

}
