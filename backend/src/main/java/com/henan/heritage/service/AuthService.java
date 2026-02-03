package com.henan.heritage.service;

import com.henan.heritage.entity.AdminUser;

public interface AuthService {
    AdminUser login(String username, String password);
    String generateToken(String username);
}