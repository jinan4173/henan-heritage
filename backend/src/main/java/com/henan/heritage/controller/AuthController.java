package com.henan.heritage.controller;

import com.henan.heritage.entity.User;
import com.henan.heritage.service.UserService;
import com.henan.heritage.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> loginData) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("登录请求开始，接收到的数据: " + loginData);
            
            String username = (String) loginData.get("username");
            String password = (String) loginData.get("password");
            
            System.out.println("用户名: " + username + ", 密码: " + password);

            // 验证用户名和密码
            System.out.println("调用userService.getByUsername方法");
            User user = userService.getByUsername(username);
            System.out.println("获取用户结果: " + user);
            
            if (user == null) {
                result.put("success", false);
                result.put("message", "用户不存在");
                System.out.println("登录失败: 用户不存在");
                return result;
            }

            // 简单的密码验证（实际项目中应该使用加密密码）
            System.out.println("验证密码");
            if (!password.equals(user.getPassword())) {
                result.put("success", false);
                result.put("message", "密码错误");
                System.out.println("登录失败: 密码错误");
                return result;
            }

            // 移除密码字段，避免安全问题
            user.setPassword(null);

            // 生成 token
            System.out.println("生成JWT token");
            String token = JwtUtils.generateToken(username);
            System.out.println("生成的token: " + token);

            result.put("success", true);
            result.put("user", user);
            result.put("token", token);
            result.put("message", "登录成功");
            System.out.println("登录成功，返回结果: " + result);
        } catch (Exception e) {
            System.out.println("登录异常: " + e.getMessage());
            e.printStackTrace();
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
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "认证测试失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/test-post")
    public Map<String, Object> testPost(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("接收到POST请求数据: " + data);
            result.put("success", true);
            result.put("message", "POST测试成功");
            result.put("data", data);
        } catch (Exception e) {
            System.out.println("POST测试异常: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "POST测试失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> registerData) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("注册请求开始，接收到的数据: " + registerData);
            
            String username = (String) registerData.get("username");
            String password = (String) registerData.get("password");
            String email = (String) registerData.get("email");
            
            System.out.println("用户名: " + username + ", 邮箱: " + email);

            // 检查用户名是否已存在
            System.out.println("检查用户名是否已存在");
            User existingUser = userService.getByUsername(username);
            System.out.println("检查结果: " + existingUser);
            
            if (existingUser != null) {
                result.put("success", false);
                result.put("message", "用户名已存在");
                System.out.println("注册失败: 用户名已存在");
                return result;
            }

            // 创建新用户
            System.out.println("创建新用户");
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password); // 实际项目中应该使用加密密码
            newUser.setEmail(email);
            newUser.setIsAdmin(0); // 默认为普通用户
            newUser.setStatus(1); // 1表示正常

            // 保存用户到数据库
            System.out.println("保存用户到数据库");
            userService.save(newUser);
            System.out.println("用户保存成功");

            result.put("success", true);
            result.put("message", "注册成功");
            System.out.println("注册成功，返回结果: " + result);
        } catch (Exception e) {
            System.out.println("注册异常: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "注册失败: " + e.getMessage());
        }
        return result;
    }
}
