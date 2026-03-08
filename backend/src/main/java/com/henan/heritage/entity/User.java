package com.henan.heritage.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer isAdmin; // 0: 普通用户, 1: 管理员
    private Integer status; // 1: 启用, 0: 禁用
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
