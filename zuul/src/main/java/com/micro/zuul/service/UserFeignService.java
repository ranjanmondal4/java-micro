package com.micro.zuul.service;

import com.micro.zuul.domain.User;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserRegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-server")
public interface UserFeignService {

    @RequestMapping("/api/v1/user/login")
    User login(@RequestBody LoginDto loginDto);

    @GetMapping("/api/v1/user/{userId}")
    User getUserDetails(@PathVariable("userId") String userId);

    @PostMapping("/api/v1/user/register")
    User register(@RequestBody UserRegisterDto dto);

    @PostMapping("/api/v1/admin/register")
    User registerAdmin(@RequestBody UserRegisterDto dto);

    @RequestMapping("/api/v1/admin/login")
    User loginAdmin(@RequestBody LoginDto loginDto);

    @GetMapping("/api/v1/admin/{adminId}")
    User getAdminDetails(@PathVariable("adminId") String adminId);
}

