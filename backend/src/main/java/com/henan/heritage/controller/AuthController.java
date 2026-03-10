package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.User;
import com.henan.heritage.service.UserService;
import com.henan.heritage.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, Object> loginData) {
        logger.info("登录请求开始，接收到的数据: {}", loginData);
        
        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");
        
        logger.info("用户登录: {}", username);

        // 验证用户名和密码
        User user = userService.getByUsername(username);
        logger.info("获取用户结果: {}", user);
        
        if (user == null) {
            logger.warn("登录失败: 用户不存在 - {}", username);
            return Result.badRequest("用户不存在");
        }

        // 验证密码（支持明文密码）
        boolean passwordMatch;
        
        // 检查密码是否是BCrypt格式
        String userPassword = user.getPassword();
        if (userPassword != null && userPassword.startsWith("$2a$")) {
            // 使用密码加密器验证（适用于加密的密码）
            passwordMatch = passwordEncoder.matches(password, userPassword);
            logger.info("使用BCrypt密码验证: {}", passwordMatch);
        } else {
            // 直接比较明文（适用于未加密的密码）
            passwordMatch = password.equals(userPassword);
            logger.info("使用明文密码验证: {}", passwordMatch);
        }
        
        if (!passwordMatch) {
            logger.warn("登录失败: 密码错误 - {}", username);
            return Result.badRequest("密码错误");
        }

        // 移除密码字段，避免安全问题
        user.setPassword(null);

        // 生成 token
        String token = JwtUtils.generateToken(username);
        logger.info("登录成功，生成token: {}", token);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, Object> registerData) {
        logger.info("注册请求开始，接收到的数据: {}", registerData);
        
        String username = (String) registerData.get("username");
        String password = (String) registerData.get("password");
        String email = (String) registerData.get("email");
        
        logger.info("用户注册: {}, {}", username, email);

        // 检查用户名是否已存在
        User existingUser = userService.getByUsername(username);
        logger.info("检查用户名是否已存在: {}", existingUser);
        
        if (existingUser != null) {
            logger.warn("注册失败: 用户名已存在 - {}", username);
            return Result.badRequest("用户名已存在");
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // 使用加密密码
        newUser.setEmail(email);
        newUser.setIsAdmin(0); // 默认为普通用户
        newUser.setStatus(1); // 1表示正常

        // 保存用户到数据库
        userService.save(newUser);
        logger.info("用户注册成功: {}", username);

        return Result.success("注册成功");
    }
}
