package com.henan.heritage.dto;

import java.util.Date;

/**
 * 传承人DTO类
 * 用于规范传承人接口返回格式
 */
public class InheritorDTO {
    /**
     * 传承人ID
     */
    private Long id;
    
    /**
     * 传承人姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 出生日期
     */
    private Date birthDate;
    
    /**
     * 所属地区
     */
    private String region;
    
    /**
     * 级别
     */
    private String level;
    
    /**
     * 传承项目
     */
    private String heritageItem;
    
    /**
     * 个人简介
     */
    private String biography;
    
    /**
     * 照片
     */
    private String photo;
    
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
    
    // getter 和 setter 方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getHeritageItem() {
        return heritageItem;
    }
    
    public void setHeritageItem(String heritageItem) {
        this.heritageItem = heritageItem;
    }
    
    public String getBiography() {
        return biography;
    }
    
    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
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
}