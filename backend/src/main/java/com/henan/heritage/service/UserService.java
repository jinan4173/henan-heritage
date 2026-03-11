package com.henan.heritage.service;

import com.henan.heritage.entity.User;
import java.util.List;

public interface UserService {
    List<User> listAll();
    User getById(Long id);
    User getByUsername(String username);
    void save(User user);
    void update(User user);
    void delete(Long id);
    long count();
    java.util.Map<String, Long> getUserRegistrationTrend();
}
