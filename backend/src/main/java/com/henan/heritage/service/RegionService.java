package com.henan.heritage.service;

import com.henan.heritage.entity.Region;
import java.util.List;

public interface RegionService {
    List<Region> listAll();
    Region getById(Long id);
    void save(Region region);
    void update(Region region);
    void delete(Long id);
}
