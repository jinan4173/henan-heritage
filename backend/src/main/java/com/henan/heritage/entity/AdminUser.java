package com.henan.heritage.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminUser {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}