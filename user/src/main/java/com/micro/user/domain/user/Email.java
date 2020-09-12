package com.micro.user.domain.user;


import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Email {
    @NonNull
    @Indexed(unique = true)
    private String mailId;
    private boolean verified;

    public static Email of(String emailId){
        Email email = new Email();
        email.mailId = emailId;
        return email;
    }
}