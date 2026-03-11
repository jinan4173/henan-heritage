package com.henan.heritage.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HeritageItem {
    private Long id;
    private Integer serialNumber; // 序号
    private Long regionId; // 地区ID
    private String regionName; // 地区名称
    private Long categoryId; // 类别ID
    private String categoryName; // 类别名称
    private String title; // 项目名称
    private String declarationRegion; // 申报地区或单位
    private String protectionUnit; // 保护单位
    private String description; // 项目介绍
    private String coverImage; // 封面图片URL
    private String videoUrl; // 视频URL
    private Integer status; // 状态: 1-启用, 0-禁用
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private Integer viewCount; // 访问量
    private Integer favoriteCount; // 收藏数
    // 关联属性
    private HeritageCategory category;
    private Region region;
    // 媒体资源列表
    private List<HeritageMedia> mediaList;
}