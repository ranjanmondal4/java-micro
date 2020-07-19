package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.domain.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
import com.micro.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    MongoTemplate mongoTemplate;

    public User saveUser(UserRegisterDto dto){
        User user = User.of(dto);
        return mongoTemplate.insert(user, "user");
//        return userRepo.save(user);
    }

    public String login(LoginDto dto){
        User user = userRepo.findByEmail(dto.getEmail());
        if(Objects.isNull(user))
            return null;
        if(!user.getPassword().equals(dto.getPassword()))
            return null;

        String token = AppUtils.generateOTP(6);
        user.setToken(token);
        userRepo.save(user);
        return token;
    }

    public User getUserDetails(String userId){
        return userRepo.findById(userId).orElse(null);
    }
}
