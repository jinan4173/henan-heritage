package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageItem;
import java.util.List;
import java.util.Map;

import com.henan.heritage.entity.HeritageMedia;

public interface HeritageItemService {
    List<HeritageItem> listAll(Integer status);
    HeritageItem getById(Long id);
    List<HeritageItem> listByCategoryAndRegion(Long categoryId, Long regionId, Integer status);
    void save(HeritageItem item);
    void update(HeritageItem item);
    void delete(Long id);
    long count();
    List<Map<String, Object>> getCityStatistics();
    
    // 媒体资源相关方法
    void saveMedia(HeritageMedia media);
    void deleteMediaByHeritageId(Long heritageItemId);
}