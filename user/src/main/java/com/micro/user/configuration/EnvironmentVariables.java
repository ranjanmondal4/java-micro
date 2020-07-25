package com.micro.user.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EnvironmentVariables {

    @Value("${app.password.length}")
    int passwordLength;

}
