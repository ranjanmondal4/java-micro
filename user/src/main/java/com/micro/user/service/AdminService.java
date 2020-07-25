package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.configuration.EnvironmentVariables;
import com.micro.user.domain.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AdminService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EnvironmentVariables environmentVariables;

    public User add(UserRegisterDto dto) {
        User user = User.of(dto, User.UserType.ADMIN);
        return mongoTemplate.insert(user, "user");
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

    public User getDetails(String adminId){
        return userRepo.findById(adminId).orElse(null);
    }
}
