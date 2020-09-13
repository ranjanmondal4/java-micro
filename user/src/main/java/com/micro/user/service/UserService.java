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
        User existingUser = userRepoImpl.findByPrimaryEmailId(dto.getEmail());
        if(!Objects.isNull(existingUser)){
            throw DataNotFoundException.of(existingUser.getPrimaryEmail().isVerified() ?
                    MessageConstants.USER_ALREADY_REGISTERED : MessageConstants.EMAIL_NOT_VERIFIED);
        }
        dto.setPassword(encoder.encode(dto.getPassword()));
        User user = User.of(dto, User.Role.DIRECT_CLIENT);
        return userRepoImpl.addUser(user);
    }

    public User login(LoginDto dto){
        User user = userRepoImpl.findByPrimaryEmailId(dto.getUserName());
        if(Objects.isNull(user)) {
            throw DataNotFoundException.of(MessageConstants.USER_NOT_REGISTERED);
        }

        if(user.getUserState() != User.UserState.ACTIVE)
            throw DataNotFoundException.of(localeService.getMessage(MessageConstants.USER_STATE,
                    user.getUserState().getValue()));

        if(!encoder.matches(dto.getPassword(), user.getPassword()))
            throw DataNotFoundException.of(MessageConstants.INVALID_PASSWORD);

        String token = AppUtils.generateToken(environmentVariables.getPasswordLength());
        user.setToken(token);
        return userRepoImpl.save(user);
    }

    public User findUserById(String userId){
        return userRepoImpl.findById(userId).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
    }
}
