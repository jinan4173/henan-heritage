package com.henan.heritage.service.impl;

import com.henan.heritage.entity.Promotion;
import com.henan.heritage.mapper.PromotionMapper;
import com.henan.heritage.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public boolean save(Promotion promotion) {
        return promotionMapper.insert(promotion) > 0;
    }

    @Override
    public boolean update(Promotion promotion) {
        return promotionMapper.update(promotion) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return promotionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteBatch(List<Long> ids) {
        return promotionMapper.deleteBatch(ids) > 0;
    }

    @Override
    public Promotion getById(Long id) {
        return promotionMapper.selectById(id);
    }

    @Override
    public List<Promotion> getAll() {
        return promotionMapper.selectAll();
    }

    @Override
    public List<Promotion> getByPage(int page, int limit, String keyword) {
        int offset = (page - 1) * limit;
        return promotionMapper.selectByPage(offset, limit, keyword);
    }

    @Override
    public int getCount(String keyword) {
        return promotionMapper.count(keyword);
    }
}