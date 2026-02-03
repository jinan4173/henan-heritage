package com.henan.heritage.service.impl;

import com.henan.heritage.entity.Inheritor;
import com.henan.heritage.mapper.InheritorMapper;
import com.henan.heritage.service.InheritorService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class InheritorServiceImpl implements InheritorService {

    @Autowired
    private InheritorMapper inheritorMapper;

    @Override
    public List<Inheritor> list() {
        Inheritor inheritor = new Inheritor();
        inheritor.setStatus(1);
        return inheritorMapper.selectList(inheritor);
    }

    @Override
    public Inheritor getById(Long id) {
        return inheritorMapper.selectById(id);
    }

    @Override
    public boolean create(Inheritor inheritor) {
        inheritor.setStatus(1);
        return inheritorMapper.insert(inheritor) > 0;
    }

    @Override
    public boolean update(Inheritor inheritor) {
        return inheritorMapper.update(inheritor) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return inheritorMapper.deleteById(id) > 0;
    }

    @Override
    public long count() {
        return inheritorMapper.count();
    }
}
