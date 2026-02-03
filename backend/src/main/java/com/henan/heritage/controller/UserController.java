package com.henan.heritage.controller;

import com.henan.heritage.entity.User;
import com.henan.heritage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> users = userService.listAll();
            // 移除密码字段，避免安全问题
            for (User user : users) {
                user.setPassword(null);
            }
            result.put("success", true);
            result.put("data", users);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getById(id);
            if (user != null) {
                user.setPassword(null); // 移除密码字段，避免安全问题
            }
            result.put("success", true);
            result.put("data", user);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.save(user);
            result.put("success", true);
            result.put("message", "保存成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.update(user);
            result.put("success", true);
            result.put("message", "更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
