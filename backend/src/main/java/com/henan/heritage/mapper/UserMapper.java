package com.henan.heritage.mapper;

import com.henan.heritage.entity.User;
import java.util.List;

public interface UserMapper {
    List<User> selectList();
    User selectById(Long id);
    User selectByUsername(String username);
    void insert(User user);
    void update(User user);
    void delete(Long id);
    long count();
}
