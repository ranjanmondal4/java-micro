package com.micro.user.configuration;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3546093099369620128L;

    public DataNotFoundException(String msg) {
        super(msg);
    }

    public static DataNotFoundException of(String msg){
        return new DataNotFoundException(msg);
    }
}
