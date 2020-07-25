package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.configuration.EnvironmentVariables;
import com.micro.user.domain.RedisUserRole;
import com.micro.user.domain.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
//import com.micro.user.repo.RedisUserRoleRepo;
import com.micro.user.repo.RedisUserRoleTemplate;
import com.micro.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    MongoTemplate mongoTemplate;
//
//    @Autowired
//    RedisUserRoleTemplate redisUserRoleTemplate;

    @Autowired
    EnvironmentVariables environmentVariables;

    public User add(UserRegisterDto dto){
        User user = User.of(dto);
        return mongoTemplate.insert(user, "user");
//        return userRepo.save(user);
    }

    public User login(LoginDto dto){
        User user = userRepo.findByEmail(dto.getEmail());
        if(Objects.isNull(user))
            return null;
        if(!user.getPassword().equals(dto.getPassword()))
            return null;

        String token = AppUtils.generateToken(environmentVariables.getPasswordLength());
        user.setToken(token);
        return userRepo.save(user);
    }

    public User getUserDetails(String userId){
        return userRepo.findById(userId).orElse(null);
    }
}
