package com.micro.user.service;

import com.micro.user.configuration.*;
import com.micro.user.domain.user.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.repo.UserRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private EnvironmentVariables environmentVariables;

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private LocaleService localeService;

    public User addUser(UserRegisterDto dto){
        dto.setPassword(encoder.encode(dto.getPassword()));
        User user = User.of(dto, User.Role.DIRECT_CLIENT);
        return userRepoImpl.addUser(user);
    }

    public User login(LoginDto dto){
        User user = userRepoImpl.findByPrimaryEmailId(dto.getUserName());
        if(Objects.isNull(user)) {
            throw DataNotFoundException.of(MessageConstants.USER_NOT_REGISTERED);
        }

        if(!encoder.matches(dto.getPassword(), user.getPassword()))
            throw DataNotFoundException.of(MessageConstants.INVALID_PASSWORD);

        String token = AppUtils.generateToken(environmentVariables.getPasswordLength());
        user.setToken(token);
        return userRepoImpl.save(user);
    }

//    public ResponseUtils.Response<? extends Object> login(LoginDto dto){
//        User user = userRepoImpl.findByPrimaryEmailId(dto.getUserName());
//        if(Objects.isNull(user)) {
//        //    throw DataNotFoundException.of(localeService.getMessage("user.not.registered"));
//            return ResponseUtils.generateResponse(false, user, localeService.getMessage("user.not.registered"));
//        }
//
//        if(!encoder.matches(dto.getPassword(), user.getPassword())) {
//            // throw DataNotFoundException.of(localeService.getMessage("invalid.password"));
//            return ResponseUtils.generateResponse(false, user, localeService.getMessage("invalid.password"));
//        }
//
//        String token = AppUtils.generateToken(environmentVariables.getPasswordLength());
//        user.setToken(token);
//        user = userRepoImpl.save(user);
//
//       return ResponseUtils.generateResponse(true, user, "user.found.successfully");
//    }



    public User getUserDetails(String userId){
        return userRepoImpl.findById(userId).orElse(null);
    }
}
