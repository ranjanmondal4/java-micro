package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.domain.User;
import com.micro.zuul.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public User getUser(){
        User user = AppUtils.getLoggedInUser();
        log.info("in side get user id {}", user.getId());
        return userService.getUserDetails(user.getId());
    }
}

