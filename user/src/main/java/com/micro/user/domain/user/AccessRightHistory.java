package com.micro.user.domain.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessRightHistory {
    private String role;
    private Action action;
    private LocalDateTime date;
    public enum  Action {
        ADDED, REMOVED
    }
}
