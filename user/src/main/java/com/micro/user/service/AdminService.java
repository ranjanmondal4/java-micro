package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.configuration.EnvironmentVariables;
import com.micro.user.domain.user.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.repo.UserRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AdminService {

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Autowired
//    UserRepo userRepo;

    @Autowired
    EnvironmentVariables environmentVariables;

    @Autowired
    private UserRepoImpl userRepoImpl;

    public User add(UserRegisterDto dto) {
        User user = User.of(dto, User.UserType.ADMIN);
        return userRepoImpl.addUser(user);
    }

    public User login(LoginDto dto){
        User user = userRepoImpl.findByPrimaryEmailId(dto.getEmail());
        if(Objects.isNull(user))
            return null;
        if(!user.getPassword().equals(dto.getPassword()))
            return null;

        String token = AppUtils.generateToken(environmentVariables.getPasswordLength());
        user.setToken(token);
        return userRepoImpl.save(user);
    }

    public User getDetails(String adminId){
        return userRepoImpl.findById(adminId).orElse(null);
    }
}
