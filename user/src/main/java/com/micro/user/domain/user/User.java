package com.micro.user.domain.user;

import com.micro.user.dto.UserRegisterDto;
import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;
    @Indexed(unique = true)
    private Email primaryEmail;
    private List<Email> secondaryEmails = new ArrayList<>();

    @NonNull
    private UserType userType;
    public enum UserType {
        USER, ADMIN;
    }
    @Transient
    private String token;

    public User(String emailId, String password){
        Email email = Email.of(emailId);
        this.primaryEmail = email;
        this.password = password;
    }

    public User(String emailId, String password, UserType userType){
        Email email = Email.of(emailId);
        this.primaryEmail = email;
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

