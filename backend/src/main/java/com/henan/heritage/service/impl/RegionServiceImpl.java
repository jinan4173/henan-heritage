package com.henan.heritage.service.impl;

import com.henan.heritage.entity.Region;
import com.henan.heritage.mapper.RegionMapper;
import com.henan.heritage.mapper.HeritageItemMapper;
import com.henan.heritage.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private HeritageItemMapper heritageItemMapper;

    @Override
    public List<Region> listAll() {
        return regionMapper.selectList();
    }

    @Override
    public Region getById(Long id) {
        return regionMapper.selectById(id);
    }

    @Override
    public void save(Region region) {
        regionMapper.insert(region);
    }

    @Override
    public void update(Region region) {
        regionMapper.update(region);
    }

    @Override
    public void delete(Long id) {
        regionMapper.delete(id);
    }

    @Override
    public Map<String, Long> getRegionStats() {
        Map<String, Long> stats = new HashMap<>();
        List<Region> regions = regionMapper.selectList();
        for (Region region : regions) {
            long count = heritageItemMapper.countByRegionId(region.getId());
            stats.put(region.getName(), count);
        }
        return stats;
    }
}
