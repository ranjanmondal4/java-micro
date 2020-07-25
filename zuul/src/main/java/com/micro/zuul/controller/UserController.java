package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.domain.User;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserRegisterDto;
//import com.micro.zuul.repo.RedisUserRoleRepo;
import com.micro.zuul.repo.RedisUserRoleTemplate;
import com.micro.zuul.service.UserService;
import com.micro.zuul.service.redis.UserRedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
//    @Autowired
//    RedisUserRoleRepo userRoleRepo;
    @Autowired
    RedisUserRoleTemplate redisUserRoleTemplate;

    @Autowired
    UserRedisService userRedisService;

    @GetMapping("/details")
    public User getUser(){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return userService.getUserDetails(user.getUserId());
    }

    @HystrixCommand(fallbackMethod = "defaultLogin", commandKey = "common-key"
            , commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
            }
    )
    @PostMapping("/login")
    String login(@RequestBody LoginDto loginDto){
        User user = userService.login(loginDto);
        RedisUserRole userRole = RedisUserRole.of(user);
        userRedisService.addUserDetails(userRole);
        //userRoleRepo.save(userRole);
//        redisUserRoleTemplate.add(userRole);

        return user.getToken();
    }

    String defaultLogin(@RequestBody LoginDto loginDto){
        log.info("default login called");
        return "abcd";
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDto dto){
        return userService.register(dto);
    }

}

