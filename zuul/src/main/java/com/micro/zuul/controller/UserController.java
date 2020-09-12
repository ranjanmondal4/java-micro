package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.configuration.LocaleService;
import com.micro.zuul.configuration.MessageConstant;
import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserDto;
import com.micro.zuul.dto.UserRegisterDto;
//import com.micro.zuul.repo.RedisUserRoleRepo;
import com.micro.zuul.repo.RedisUserRoleTemplate;
import com.micro.zuul.service.UserFeignService;
import com.micro.zuul.service.redis.UserRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserFeignService userFeignService;

    @Autowired
    RedisUserRoleTemplate redisUserRoleTemplate;

    @Autowired
    UserRedisService userRedisService;

    @Autowired
    LocaleService localeService;

    @GetMapping("/details")
    public ResponseEntity<Object> getUser(){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.getUserDetails(user.getUserId()));
    }

    /*@HystrixCommand(fallbackMethod = "defaultLogin", commandKey = "common-key"
            , commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
            }
    )*/
    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody LoginDto loginDto){
        ResponseUtils.Response<UserDto> response = userFeignService.login(loginDto);
        if(response.isSuccess()){
            UserDto user = response.getData();
            RedisUserRole userRole = RedisUserRole.of(user);
            userRedisService.addUserDetails(userRole);
            Map<String, Object> data = new HashMap<>();
            data.put("token", user.getToken());
            return ResponseUtils.generate(response.isSuccess(), data, response.getMessage());
        }

        return ResponseUtils.generate(response);
    }

    /*String defaultLogin(@RequestBody LoginDto loginDto){
        log.info("default login called");
        return "Default abcd";
    }*/

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto dto){
        return ResponseUtils.generate(userFeignService.register(dto));
    }

}