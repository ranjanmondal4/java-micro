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
//    @Autowired
//    RedisUserRoleRepo userRoleRepo;
    @Autowired
    RedisUserRoleTemplate redisUserRoleTemplate;

    @Autowired
    UserRedisService userRedisService;

    @Autowired
    LocaleService localeService;

    @GetMapping("/details")
    public ResponseEntity<Object> getUser(){
        RedisUserRole user = AppUtils.getLoggedInUser();
        UserDto userDto = userFeignService.getUserDetails(user.getUserId());
        return ResponseUtils.generate(!Objects.isNull(userDto), userDto,
                localeService.getMessage(MessageConstant.DATA_FOUND));
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
        UserDto user = userFeignService.login(loginDto);
        RedisUserRole userRole = RedisUserRole.of(user);
        userRedisService.addUserDetails(userRole);
        Map<String, Object> data = new HashMap<>();
        data.put("token", user.getToken());
        return ResponseUtils.generate(!Objects.isNull(user), data, localeService.getMessage(MessageConstant.CONFIRMED_PASSWORD_NOT_FOUND));
    }

    /*String defaultLogin(@RequestBody LoginDto loginDto){
        log.info("default login called");
        return "Default abcd";
    }*/

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto dto){
        return userFeignService.register(dto);
    }

}

