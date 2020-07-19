package com.micro.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterDto {
    private String email;
    private String password;
    private String confirmedPassword;
}
