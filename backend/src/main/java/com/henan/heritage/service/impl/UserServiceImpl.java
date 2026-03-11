package com.henan.heritage.service.impl;

import com.henan.heritage.entity.User;
import com.henan.heritage.mapper.UserMapper;
import com.henan.heritage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        logger.info("获取所有用户");
        List<User> users = userMapper.selectList();
        logger.info("获取用户数量: {}", users.size());
        return users;
    }

    @Override
    public User getById(Long id) {
        logger.info("根据ID获取用户: {}", id);
        User user = userMapper.selectById(id);
        logger.info("获取用户结果: {}", user);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        logger.info("根据用户名获取用户: {}", username);
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            logger.info("获取用户成功: {}", user.getUsername());
            return user;
        }
        logger.info("用户不存在: {}", username);
        return null;
    }

    @Override
    public void save(User user) {
        logger.info("保存用户: {}", user);
        userMapper.insert(user);
        logger.info("保存用户成功");
    }

    @Override
    public void update(User user) {
        logger.info("更新用户: {}", user);
        userMapper.update(user);
        logger.info("更新用户成功");
    }

    @Override
    public void delete(Long id) {
        logger.info("删除用户: {}", id);
        userMapper.delete(id);
        logger.info("删除用户成功");
    }

    @Override
    public long count() {
        logger.info("统计用户数量");
        // 使用SQL COUNT查询，提高性能
        long count = userMapper.count();
        logger.info("用户数量: {}", count);
        return count;
    }

    @Override
    public java.util.Map<String, Long> getUserRegistrationTrend() {
        logger.info("获取用户注册趋势");
        java.util.Map<String, Long> trend = userMapper.getUserRegistrationTrend();
        logger.info("用户注册趋势: {}", trend);
        return trend;
    }
}
