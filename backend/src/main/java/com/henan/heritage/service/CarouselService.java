package com.henan.heritage.service;

import com.henan.heritage.entity.Carousel;
import java.util.List;

/**
 * 轮播图Service接口
 */
public interface CarouselService {
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
     * @return 是否添加成功
     */
    boolean add(Carousel carousel);
    
    /**
     * 更新轮播图
     * @param carousel 轮播图对象
     * @return 是否更新成功
     */
    boolean update(Carousel carousel);
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 是否删除成功
     */
    boolean delete(Long id);
    
    /**
     * 获取启用的轮播图列表（用于前端展示）
     * @return 启用的轮播图列表
     */
    List<Carousel> getEnabledList();
}
