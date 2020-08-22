package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.configuration.EnvironmentVariables;
import com.micro.user.domain.user.User;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserRegisterDto;
//import com.micro.user.repo.RedisUserRoleRepo;
import com.micro.user.repo.UserRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private EnvironmentVariables environmentVariables;

    @Autowired
    private UserRepoImpl userRepoImpl;

    public User addUser(UserRegisterDto dto){
        User user = User.of(dto);
        return userRepoImpl.addUser(user);
//        return userRepo.save(user);
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

    public User getUserDetails(String userId){
        return userRepoImpl.findById(userId).orElse(null);
    }
}
