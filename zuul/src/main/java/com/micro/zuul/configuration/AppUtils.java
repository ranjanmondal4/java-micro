package com.micro.zuul.configuration;

import com.micro.zuul.domain.RedisUserRole;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static RedisUserRole getLoggedInUser() {
        return (RedisUserRole) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
