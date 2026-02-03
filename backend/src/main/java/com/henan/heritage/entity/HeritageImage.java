package com.henan.heritage.entity;

import lombok.Data;
import java.util.Date;

@Data
public class HeritageImage {
    private Long id;
    private Long heritageId;
    private String imageUrl;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
}