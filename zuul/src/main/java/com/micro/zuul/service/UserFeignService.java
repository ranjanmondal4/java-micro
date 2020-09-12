package com.micro.zuul.service;

import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserDto;
import com.micro.zuul.dto.UserRegisterDto;
import com.micro.zuul.dto.document.AddFolderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-server")
public interface UserFeignService {

    @RequestMapping("/api/v1/user/login")
    ResponseUtils.Response<UserDto> login(@RequestBody LoginDto loginDto);


    @GetMapping("/api/v1/user/{userId}")
    ResponseUtils.Response<UserDto> getUserDetails(@PathVariable("userId") String userId);

    @PostMapping("/api/v1/user/register")
    ResponseUtils.Response<UserDto> register(@RequestBody UserRegisterDto dto);

    @PostMapping("/api/v1/user/folder")
    ResponseEntity<Object> addUserFolder(@RequestBody AddFolderDTO dto, @RequestParam String userId);

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

