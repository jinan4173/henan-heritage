package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageItem;
import java.util.List;
import java.util.Map;

public interface HeritageItemMapper {
    List<HeritageItem> selectWithDetails(Integer status);
    HeritageItem selectByIdWithDetails(Long id);
    List<HeritageItem> selectByCategoryAndRegion(Long categoryId, Long regionId, Integer status);
    HeritageItem selectById(Long id);
    void insert(HeritageItem item);
    void update(HeritageItem item);
    void delete(Long id);
    long count();
    long countByCategoryId(Long categoryId);
    long countByRegionId(Long regionId);
    List<Map<String, Object>> selectCityStatistics();
    List<Map<String, Object>> selectCategoryStatistics();
    List<HeritageItem> selectPopular(int limit);
    void incrementViewCount(Long id);
    void incrementFavoriteCount(Long id);
    void decrementFavoriteCount(Long id);
}