package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageCategory;
import java.util.List;

public interface HeritageCategoryService {
    List<HeritageCategory> listAll();
    HeritageCategory getById(Long id);
    void save(HeritageCategory category);
    void update(HeritageCategory category);
    void delete(Long id);
}
