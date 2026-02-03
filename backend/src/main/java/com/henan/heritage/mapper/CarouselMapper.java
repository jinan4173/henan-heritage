package com.henan.heritage.mapper;

import com.henan.heritage.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 轮播图Mapper接口
 */
@Mapper
public interface CarouselMapper {
    /**
     * 获取轮播图列表
     * @return 轮播图列表
     */
    List<Carousel> list();
    
    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    Carousel getById(Long id);
    
    /**
     * 添加轮播图
     * @param carousel 轮播图对象
     * @return 影响行数
     */
    int add(Carousel carousel);
    
    /**
     * 更新轮播图
     * @param carousel 轮播图对象
     * @return 影响行数
     */
    int update(Carousel carousel);
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 影响行数
     */
    int delete(Long id);
    
    /**
     * 获取启用的轮播图列表（用于前端展示）
     * @return 启用的轮播图列表
     */
    List<Carousel> getEnabledList();
}
