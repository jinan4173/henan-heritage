package com.henan.heritage.entity;

import lombok.Data;
import java.util.Date;

@Data
public class HeritageItem {
    private Long id;
    private Integer serialNumber; // 序号
    private String projectSerialNumber; // 项目序号
    private String projectCode; // 编号
    private String title; // 名称
    private String description;
    private String categoryName; // 类别
    private String announcementTime; // 公布时间
    private String projectType; // 类型
    private String declarationRegion; // 申报地区或单位
    private String protectionUnit; // 保护单位
    private Long categoryId;
    private Long regionId;
    private String coverImage;
    private String videoUrl;
    private String projectIntroduction; // 项目介绍
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
    // 关联属性
    private HeritageCategory category;
    private Region region;
    // 媒体资源列表
    private java.util.List<HeritageMedia> mediaList;
}