package com.micro.user.dto;

import com.micro.user.domain.user.Email;
import com.micro.user.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private Email primaryEmail;
    private List<Email> secondaryEmails = new ArrayList<>();
    private List<User.Role> roles;
    private String token;
}