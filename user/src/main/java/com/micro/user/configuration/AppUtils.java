package com.micro.user.configuration;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

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

    public static int nextCount(List<String> names){
        int max = 0;
        for(String name : names){
            int initial = name.indexOf("(");
            int last = name.indexOf(")");
            if(initial != -1 && initial < last){
                try {
                    int digit = Integer.parseInt(name.substring(initial+1, last));
                    System.out.println("Digit " + digit);
                    max = digit > max ? digit : max;
                }catch (Exception e){

                }
            }
        }
        return max == 0 ? 2 : max + 1;
    }
}
