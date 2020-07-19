package com.micro.user.domain;

import com.micro.user.dto.UserRegisterDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
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
    private String token;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static User of(UserRegisterDto dto){
        return new User(dto.getEmail(), dto.getPassword());
    }
}
