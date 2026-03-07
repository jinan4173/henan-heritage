package com.henan.heritage.mapper;

import com.henan.heritage.entity.Promotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PromotionMapper {
    // 新增宣传
    int insert(Promotion promotion);
    
    // 更新宣传
    int update(Promotion promotion);
    
    // 删除宣传
    int deleteById(Long id);
    
    // 批量删除宣传
    int deleteBatch(@Param("ids") List<Long> ids);
    
    // 根据ID查询宣传
    Promotion selectById(Long id);
    
    // 查询所有宣传
    List<Promotion> selectAll();
    
    // 分页查询宣传
    List<Promotion> selectByPage(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword);
    
    // 查询宣传总数
    int count(@Param("keyword") String keyword);
}