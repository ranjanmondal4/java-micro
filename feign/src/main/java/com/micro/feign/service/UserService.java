package com.micro.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-server")
public interface UserService {

    @GetMapping("/api/v1/user")
    String getUserName();
}
