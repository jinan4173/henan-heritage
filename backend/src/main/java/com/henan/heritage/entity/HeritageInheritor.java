package com.henan.heritage.entity;

import lombok.Data;
import java.util.Date;

@Data
public class HeritageInheritor {
    private Long id;
    private String name;
    private String gender;
    private String ethnicity;
    private String level;
    private Long projectId;
    private Long regionId;
    private String introduction;
    private String photoUrl;
    private Date createTime;
    private String birthYear;
    private String description;
    private String photo;
    // 关联属性
    private HeritageItem project;
    private Region region;
}