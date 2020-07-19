package com.micro.user.controller;

import com.micro.user.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping()
    public String getUserName(){ // http://localhost:8087/api/v1/user/1/cabinet
        log.info("Inside user name");
        return "User - Ranjan";
//        return restTemplate.getForObject("http://cabinet-server/api/v1/user/1/cabinet", String.class);
//        return "User - Ranjan";
    }

    @RequestMapping("/login")
    public String login(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
        log.info("Inside login");
        if(loginDto.getEmail().equals("ranjan") && loginDto.getPassword().equals("test"))
            return "mytoken";
        return "xyz";
    }
}

