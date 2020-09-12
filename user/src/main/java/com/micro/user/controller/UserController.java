package com.micro.user.controller;

import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.configuration.ResponseUtils;
import com.micro.user.domain.user.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserDto;
import com.micro.user.dto.UserDtoMapper;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    LocaleService localeService;

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
    public ResponseUtils.Response<UserDto> login(@RequestBody LoginDto loginDto){
        User user = userService.login(loginDto);
        UserDto userDto = userDtoMapper.toUserDto(user);
        return ResponseUtils.generateResponse(true, userDto, localeService.getMessage(MessageConstants.USER_FOUND));
    }


    /*public User defaultLogin(@RequestBody LoginDto loginDto){ // http://localhost:8087/api/v1/user/1/cabinet
//        log.info("comes here");
        return new User();
    }*/

}

