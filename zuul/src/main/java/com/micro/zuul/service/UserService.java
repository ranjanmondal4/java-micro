package com.micro.zuul.service;

import com.micro.zuul.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-server")
public interface UserService {

    @GetMapping("/api/v1/user/{userId}")
    User getUserDetails(@PathVariable("userId") String userId);
}

