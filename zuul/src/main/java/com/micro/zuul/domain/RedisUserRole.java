package com.micro.zuul.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

    @Override
    public String toString() {
        return "{ userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", roles=" + roles +
                '}';
    }
}
