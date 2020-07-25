package com.micro.zuul.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EnvironmentVariables {
    public static final long tokenExpiry = 60*60;

}
