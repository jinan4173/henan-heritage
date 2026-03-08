package com.henan.heritage.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HeritageMedia {
    private Long id;
    private Long heritageItemId; // 对应数据库的 heritage_item_id
    private String mediaType; // image / video
    private String mediaUrl;
    private String source;
    private String description;
    private LocalDateTime createTime;
}
