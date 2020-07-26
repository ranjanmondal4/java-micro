package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppConstants;
import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.configuration.JwtTokenProvider;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.domain.User;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserRegisterDto;
import com.micro.zuul.service.UserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    @Autowired
    UserFeignService userFeignService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDto dto){
        return userFeignService.registerAdmin(dto);
    }

    @PostMapping("/login")
    User login(@RequestBody LoginDto loginDto){
        User admin = userFeignService.loginAdmin(loginDto);
        if(!Objects.isNull(admin)){
            admin.setToken(AppConstants.BEARER_WITH_SPACE + jwtTokenProvider.createToken(admin.getEmail(), admin.getId(), Arrays.asList("ROLE_SUPER_ADMIN")));
        }else {
            admin.setToken(null);
        }
        return admin;
    }

    @GetMapping("/details")
    public User getDetails(){
        RedisUserRole user = AppUtils.getLoggedInUser();
//        log.info("user id {}", user.getUserId());
        return userFeignService.getAdminDetails(user.getUserId());
    }
}
