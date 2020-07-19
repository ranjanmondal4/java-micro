package com.micro.zuul.configuration;

import com.micro.zuul.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private User user;

    public UserAuthentication(){}
    public UserAuthentication(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return user.toString();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
