package com.henan.heritage.service.impl;

import com.henan.heritage.entity.Carousel;
import com.henan.heritage.mapper.CarouselMapper;
import com.henan.heritage.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 轮播图Service实现类
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    
    @Autowired
    private CarouselMapper carouselMapper;
    
    @Override
    public List<Carousel> list() {
        return carouselMapper.list();
    }
    
    @Override
    public Carousel getById(Long id) {
        return carouselMapper.getById(id);
    }
    
    @Override
    public boolean add(Carousel carousel) {
        return carouselMapper.add(carousel) > 0;
    }
    
    @Override
    public boolean update(Carousel carousel) {
        return carouselMapper.update(carousel) > 0;
    }
    
    @Override
    public boolean delete(Long id) {
        return carouselMapper.delete(id) > 0;
    }
    
    @Override
    public List<Carousel> getEnabledList() {
        return carouselMapper.getEnabledList();
    }
}
