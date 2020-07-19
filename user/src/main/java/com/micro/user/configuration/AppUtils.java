package com.micro.user.configuration;

import org.apache.commons.lang3.RandomStringUtils;

public class AppUtils {

    public static String generateOTP(int length) {
        String numbers = "1234567890";
        return RandomStringUtils.random(length, numbers);
    }

}
