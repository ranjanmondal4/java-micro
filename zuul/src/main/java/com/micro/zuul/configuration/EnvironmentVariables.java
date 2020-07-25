package com.micro.zuul.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EnvironmentVariables {
    public static final long tokenExpiry = 60*60;

    @Value("app.jwt.secret.key")
    private String jwtSecretKey;

    @Value("${app.jwt.expiration.in.ms}")
    private long jwtExpirationInMills;
}
