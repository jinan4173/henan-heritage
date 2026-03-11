package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageCategory;
import com.henan.heritage.mapper.HeritageCategoryMapper;
import com.henan.heritage.mapper.HeritageItemMapper;
import com.henan.heritage.service.HeritageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class HeritageCategoryServiceImpl implements HeritageCategoryService {

    @Autowired
    private HeritageCategoryMapper heritageCategoryMapper;

    @Autowired
    private HeritageItemMapper heritageItemMapper;

    @Override
    public List<HeritageCategory> listAll() {
        return heritageCategoryMapper.selectList();
    }

    @Override
    public HeritageCategory getById(Long id) {
        return heritageCategoryMapper.selectById(id);
    }

    @Override
    public void save(HeritageCategory category) {
        heritageCategoryMapper.insert(category);
    }

    @Override
    public void update(HeritageCategory category) {
        heritageCategoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        heritageCategoryMapper.delete(id);
    }

    @Override
    public Map<String, Long> getCategoryStats() {
        Map<String, Long> stats = new HashMap<>();
        List<HeritageCategory> categories = heritageCategoryMapper.selectList();
        for (HeritageCategory category : categories) {
            long count = heritageItemMapper.countByCategoryId(category.getId());
            stats.put(category.getName(), count);
        }
        return stats;
    }
}
