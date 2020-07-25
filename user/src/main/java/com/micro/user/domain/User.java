package com.micro.user.domain;

import com.micro.user.dto.UserRegisterDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private UserType userType;
    public enum UserType {
        USER, ADMIN;
    }
    @Transient
    private String token;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, UserType userType){
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public static User of(UserRegisterDto dto){
        return new User(dto.getEmail(), dto.getPassword());
    }

    public static User of(UserRegisterDto dto, UserType type){
        return new User(dto.getEmail(), dto.getPassword(), type);
    }
}
