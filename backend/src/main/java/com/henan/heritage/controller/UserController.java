package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
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
    public Result<List<User>> list() {
        List<User> users = userService.listAll();
        // 移除密码字段，避免安全问题
        for (User user : users) {
            user.setPassword(null);
        }
        return Result.success(users);
    }

    @GetMapping("/get/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null); // 移除密码字段，避免安全问题
        }
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody User user) {
        userService.save(user);
        return Result.success("保存成功");
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody User user) {
        userService.update(user);
        return Result.success("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success("删除成功");
    }
}
