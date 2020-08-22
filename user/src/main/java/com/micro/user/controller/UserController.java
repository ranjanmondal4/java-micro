package com.micro.user.controller;

import com.micro.user.domain.user.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserDto;
import com.micro.user.dto.UserDtoMapper;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserDtoMapper userDtoMapper;


    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto dto){
        User user = userService.addUser(dto);
        return userDtoMapper.toUserDto(user);
    }

    @GetMapping("/{userId}")
    public UserDto getUserName(@PathVariable("userId") String userId){ // http://localhost:8087/api/v1/user/1/cabinet
        User user = userService.getUserDetails(userId);
        return userDtoMapper.toUserDto(user);
    }



    /*@HystrixCommand(fallbackMethod = "defaultLogin" , commandKey = "common-key"
//            , commandProperties = {
//            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
//    }
    )*/
    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
        User user = userService.login(loginDto);
        return userDtoMapper.toUserDto(user);
    }


    /*public User defaultLogin(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
//        log.info("comes here");
        return new User();
    }*/

}

