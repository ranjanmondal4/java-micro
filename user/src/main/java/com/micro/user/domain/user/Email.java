package com.micro.user.domain.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Email {
    private String mailId;
    private boolean verified;

    public static Email of(String emailId){
        Email email = new Email();
        email.mailId = emailId;
        return email;
    }
}