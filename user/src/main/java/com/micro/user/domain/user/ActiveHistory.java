package com.micro.user.domain.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActiveHistory {
    private boolean active;
    private LocalDateTime date;
    private String reason;
}
