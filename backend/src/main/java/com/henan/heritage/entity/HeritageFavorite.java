package com.henan.heritage.entity;

import java.util.Date;

public class HeritageFavorite {
    private Long id;
    private Long userId;
    private Long heritageId;
    private Long mediaId;
    private Date createTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHeritageId() {
        return heritageId;
    }

    public void setHeritageId(Long heritageId) {
        this.heritageId = heritageId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
