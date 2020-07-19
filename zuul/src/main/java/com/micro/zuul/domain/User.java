package com.micro.zuul.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}