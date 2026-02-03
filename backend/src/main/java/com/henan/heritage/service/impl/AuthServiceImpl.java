package com.henan.heritage.service.impl;

import com.henan.heritage.entity.AdminUser;
import com.henan.heritage.mapper.AdminUserMapper;
import com.henan.heritage.service.AuthService;
import com.henan.heritage.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminUser login(String username, String password) {
        try {
            // 正常登录逻辑
            AdminUser user = adminUserMapper.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
            return user;
        } catch (Exception e) {
            System.out.println("登录错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("登录失败: " + e.getMessage());
        }
    }

    @Override
    public String generateToken(String username) {
        return JwtUtils.generateToken(username);
    }
}