package com.henan.heritage.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 轮播图实体类
 */
@Data
public class Carousel {
    /**
     * 轮播图ID
     */
    private Long id;
    
    /**
     * 轮播图标题
     */
    private String title;
    
    /**
     * 轮播图图片URL
     */
    private String imageUrl;
    
    /**
     * 排序序号
     */
    private Integer orderNum;
    
    /**
     * 状态: 1-启用, 0-禁用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
