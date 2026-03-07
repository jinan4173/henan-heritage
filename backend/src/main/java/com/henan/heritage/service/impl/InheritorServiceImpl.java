package com.henan.heritage.service.impl;

import com.henan.heritage.entity.Inheritor;
import com.henan.heritage.mapper.InheritorMapper;
import com.henan.heritage.service.InheritorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InheritorServiceImpl implements InheritorService {

    @Autowired
    private InheritorMapper inheritorMapper;

    @Override
    public List<Inheritor> listAll() {
        return inheritorMapper.listAll();
    }

    @Override
    public Inheritor getById(Long id) {
        return inheritorMapper.getById(id);
    }

    @Override
    public void save(Inheritor inheritor) {
        inheritorMapper.insert(inheritor);
    }

    @Override
    public void update(Inheritor inheritor) {
        inheritorMapper.update(inheritor);
    }

    @Override
    public void delete(Long id) {
        inheritorMapper.delete(id);
    }

    @Override
    public long count() {
        // 从数据库获取
        List<Inheritor> inheritors = inheritorMapper.listAll();
        return inheritors.size();
    }
}
