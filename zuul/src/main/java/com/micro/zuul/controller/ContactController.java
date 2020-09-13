package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.dto.ContactDto;
import com.micro.zuul.service.UserFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private UserFeignService userFeignService;

    @ApiOperation("add contacts")
    @PostMapping("/contact")
    public ResponseEntity<Object> addContact(@RequestBody ContactDto dto){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.addContact(dto, user.getUserId()));
    }
}
