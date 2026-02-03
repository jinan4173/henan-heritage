package com.henan.heritage.service.impl;

import com.henan.heritage.entity.HeritageCategory;
import com.henan.heritage.mapper.HeritageCategoryMapper;
import com.henan.heritage.service.HeritageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HeritageCategoryServiceImpl implements HeritageCategoryService {

    @Autowired
    private HeritageCategoryMapper heritageCategoryMapper;

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
}
