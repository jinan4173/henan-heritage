package com.henan.heritage.dto;

import java.util.Date;
import java.util.List;

/**
 * 非遗项目DTO类
 * 用于规范非遗项目接口返回格式
 */
public class HeritageItemDTO {
    /**
     * 项目ID
     */
    private Long id;
    
    /**
     * 项目名称
     */
    private String title;
    
    /**
     * 项目描述
     */
    private String description;
    
    /**
     * 申报地区
     */
    private String declarationRegion;
    
    /**
     * 保护单位
     */
    private String protectionUnit;
    
    /**
     * 级别
     */
    private String level;
    
    /**
     * 批次
     */
    private String batch;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 地区ID
     */
    private Long regionId;
    
    /**
     * 地区名称
     */
    private String regionName;
    
    /**
     * 封面图片
     */
    private String coverImage;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 更新时间
     */
    private Date updatedAt;
    
    /**
     * 图片列表
     */
    private List<HeritageImageDTO> images;
    
    /**
     * 传承人列表
     */
    private List<InheritorDTO> inheritors;
    
    // getter 和 setter 方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDeclarationRegion() {
        return declarationRegion;
    }
    
    public void setDeclarationRegion(String declarationRegion) {
        this.declarationRegion = declarationRegion;
    }
    
    public String getProtectionUnit() {
        return protectionUnit;
    }
    
    public void setProtectionUnit(String protectionUnit) {
        this.protectionUnit = protectionUnit;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getBatch() {
        return batch;
    }
    
    public void setBatch(String batch) {
        this.batch = batch;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public String getRegionName() {
        return regionName;
    }
    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    public String getCoverImage() {
        return coverImage;
    }
    
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<HeritageImageDTO> getImages() {
        return images;
    }
    
    public void setImages(List<HeritageImageDTO> images) {
        this.images = images;
    }
    
    public List<InheritorDTO> getInheritors() {
        return inheritors;
    }
    
    public void setInheritors(List<InheritorDTO> inheritors) {
        this.inheritors = inheritors;
    }
}
