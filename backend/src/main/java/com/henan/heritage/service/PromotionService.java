package com.henan.heritage.service;

import com.henan.heritage.entity.Promotion;

import java.util.List;

public interface PromotionService {
    // 新增宣传
    boolean save(Promotion promotion);
    
    // 更新宣传
    boolean update(Promotion promotion);
    
    // 删除宣传
    boolean delete(Long id);
    
    // 批量删除宣传
    boolean deleteBatch(List<Long> ids);
    
    // 根据ID查询宣传
    Promotion getById(Long id);
    
    // 查询所有宣传
    List<Promotion> getAll();
    
    // 分页查询宣传
    List<Promotion> getByPage(int page, int limit, String keyword);
    
    // 查询宣传总数
    int getCount(String keyword);
}