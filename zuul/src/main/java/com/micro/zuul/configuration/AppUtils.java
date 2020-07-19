package com.micro.zuul.configuration;

import com.micro.zuul.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
