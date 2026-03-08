package com.henan.heritage.controller;

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
    public Map<String, Object> login(@RequestBody Map<String, Object> loginData) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.info("登录请求开始，接收到的数据: {}", loginData);
            
            String username = (String) loginData.get("username");
            String password = (String) loginData.get("password");
            
            logger.info("用户登录: {}", username);

            // 验证用户名和密码
            User user = userService.getByUsername(username);
            logger.info("获取用户结果: {}", user);
            
            if (user == null) {
                result.put("success", false);
                result.put("message", "用户不存在");
                logger.warn("登录失败: 用户不存在 - {}", username);
                return result;
            }

            // 验证密码（支持明文密码）
            boolean passwordMatch = false;
            
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
                result.put("success", false);
                result.put("message", "密码错误");
                logger.warn("登录失败: 密码错误 - {}", username);
                return result;
            }

            // 移除密码字段，避免安全问题
            user.setPassword(null);

            // 生成 token
            String token = JwtUtils.generateToken(username);
            logger.info("登录成功，生成token: {}", token);

            result.put("success", true);
            result.put("user", user);
            result.put("token", token);
            result.put("message", "登录成功");
            logger.info("登录成功: {}", username);
        } catch (Exception e) {
            logger.error("登录异常: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "登录失败: " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "认证测试成功");
            logger.info("认证测试成功");
        } catch (Exception e) {
            logger.error("认证测试异常: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "认证测试失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/test-post")
    public Map<String, Object> testPost(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.info("接收到POST请求数据: {}", data);
            result.put("success", true);
            result.put("message", "POST测试成功");
            result.put("data", data);
            logger.info("POST测试成功");
        } catch (Exception e) {
            logger.error("POST测试异常: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "POST测试失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> registerData) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.info("注册请求开始，接收到的数据: {}", registerData);
            
            String username = (String) registerData.get("username");
            String password = (String) registerData.get("password");
            String email = (String) registerData.get("email");
            
            logger.info("用户注册: {}, {}", username, email);

            // 检查用户名是否已存在
            User existingUser = userService.getByUsername(username);
            logger.info("检查用户名是否已存在: {}", existingUser);
            
            if (existingUser != null) {
                result.put("success", false);
                result.put("message", "用户名已存在");
                logger.warn("注册失败: 用户名已存在 - {}", username);
                return result;
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

            result.put("success", true);
            result.put("message", "注册成功");
        } catch (Exception e) {
            logger.error("注册异常: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "注册失败: " + e.getMessage());
        }
        return result;
    }
}
