package com.micro.user.controller;

import com.micro.user.domain.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDto dto){
        return userService.saveUser(dto);
    }

    @GetMapping("/{userId}")
    public User getUserName(@PathVariable("userId") String userId){ // http://localhost:8087/api/v1/user/1/cabinet
        log.info("Inside user name {}", userId );
        return userService.getUserDetails(userId);
//        return userId;
//        return restTemplate.getForObject("http://cabinet-server/api/v1/user/1/cabinet", String.class);
//        return "User - Ranjan";
    }



    @HystrixCommand(fallbackMethod = "defaultLogin" , commandKey = "common-key"
//            , commandProperties = {
//            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
//    }
    )
    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
        return userService.login(loginDto);
    }


    public User defaultLogin(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
        log.info("comes here");
        return new User();
    }

}

