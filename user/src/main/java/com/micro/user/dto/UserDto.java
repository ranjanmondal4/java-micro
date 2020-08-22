package com.micro.user.dto;

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
    private String userName;
    private String password;
    private Email primaryEmail;
    private List<Email> secondaryEmails = new ArrayList<>();

    private UserType userType;
    public enum UserType {
        USER, ADMIN;
    }

    private String token;

}


@Getter
@Setter
@NoArgsConstructor
class Email {
    private String mailId;
    private boolean verified;

    public static Email of(String emailId) {
        Email email = new Email();
        email.mailId = emailId;
        return email;
    }
}