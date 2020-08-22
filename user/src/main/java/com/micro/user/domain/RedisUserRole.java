package com.micro.user.domain;

import com.micro.user.domain.user.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
//@RedisHash
public class RedisUserRole implements Serializable {
    private static final long serialVersionUID = -2601175617857390837L;
//    @Id
    private String userId;
//    @Indexed
    private String token;
    private List<String> roles;

    public static RedisUserRole of(User user){
        RedisUserRole userRole = new RedisUserRole();
        userRole.userId = user.getId();
        userRole.token = user.getToken();
        userRole.roles = Arrays.asList("ROLE_USER", "ROLE_USER_VIEW");
        return userRole;
    }

    public static RedisUserRole of(String userId, String token, List<String> roles){
        RedisUserRole userRole = new RedisUserRole();
        userRole.userId = userId;
        userRole.token = token;
        userRole.roles = roles;
        return userRole;
    }
}
