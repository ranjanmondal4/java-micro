package com.micro.user.controller;

import com.micro.user.domain.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDto dto){
        return adminService.add(dto);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
        return adminService.login(loginDto);
    }

    @GetMapping("/{adminId}")
    public User getDetails(@PathVariable("adminId") String adminId){
        return adminService.getDetails(adminId);
    }

}
