package com.henan.heritage.service.impl;

import com.henan.heritage.entity.User;
import com.henan.heritage.mapper.UserMapper;
import com.henan.heritage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        // 从数据库获取
        List<User> users = userMapper.selectList();
        return users;
    }

    @Override
    public User getById(Long id) {
        // 从数据库获取
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        System.out.println("开始获取用户: " + username);
        System.out.println("尝试从数据库获取用户");
        // 从数据库获取
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            System.out.println("从数据库获取用户成功: " + user.getUsername());
            return user;
        }
        System.out.println("从数据库获取用户失败，用户不存在");
        return null;
    }

    @Override
    public void save(User user) {
        System.out.println("保存用户: " + user);
        userMapper.insert(user);
        System.out.println("保存用户成功");
    }

    @Override
    public void update(User user) {
        System.out.println("更新用户: " + user);
        userMapper.update(user);
        System.out.println("更新用户成功");
    }

    @Override
    public void delete(Long id) {
        System.out.println("删除用户: " + id);
        userMapper.delete(id);
        System.out.println("删除用户成功");
    }

    @Override
    public long count() {
        // 从数据库获取
        List<User> users = userMapper.selectList();
        return users.size();
    }
}
