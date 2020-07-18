package com.micro.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/user")
    public String getUserName(){ // http://localhost:8087/api/v1/user/1/cabinet
        return "User - Ranjan";
//        return restTemplate.getForObject("http://cabinet-server/api/v1/user/1/cabinet", String.class);
//        return "User - Ranjan";
    }
}

