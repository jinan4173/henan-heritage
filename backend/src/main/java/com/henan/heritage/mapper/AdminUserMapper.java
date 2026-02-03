package com.henan.heritage.mapper;

import com.henan.heritage.entity.AdminUser;

public interface AdminUserMapper {
    AdminUser findByUsername(String username);
    AdminUser selectById(Long id);
    void insert(AdminUser user);
    void update(AdminUser user);
    void delete(Long id);
}