package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageCategory;
import java.util.List;

public interface HeritageCategoryMapper {
    List<HeritageCategory> selectList();
    HeritageCategory selectById(Long id);
    void insert(HeritageCategory category);
    void update(HeritageCategory category);
    void delete(Long id);
}
