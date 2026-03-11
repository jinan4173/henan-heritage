package com.henan.heritage.service;

import com.henan.heritage.entity.Region;
import java.util.List;
import java.util.Map;

public interface RegionService {
    List<Region> listAll();
    Region getById(Long id);
    void save(Region region);
    void update(Region region);
    void delete(Long id);
    Map<String, Long> getRegionStats();
}
