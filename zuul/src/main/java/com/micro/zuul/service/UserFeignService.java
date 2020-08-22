package com.micro.zuul.service;

import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserDto;
import com.micro.zuul.dto.UserRegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-server")
public interface UserFeignService {

    @RequestMapping("/api/v1/user/login")
    UserDto login(@RequestBody LoginDto loginDto);

    @GetMapping("/api/v1/user/{userId}")
    UserDto getUserDetails(@PathVariable("userId") String userId);

    @PostMapping("/api/v1/user/register")
    UserDto register(@RequestBody UserRegisterDto dto);
//
//    @PostMapping("/api/v1/admin/register")
//    User registerAdmin(@RequestBody UserRegisterDto dto);
//
//    @RequestMapping("/api/v1/admin/login")
//    User loginAdmin(@RequestBody LoginDto loginDto);
//
//    @GetMapping("/api/v1/admin/{adminId}")
//    User getAdminDetails(@PathVariable("adminId") String adminId);
}

