package com.henan.heritage.mapper;

import com.henan.heritage.entity.Region;
import java.util.List;

public interface RegionMapper {
    List<Region> selectList();
    Region selectById(Long id);
    void insert(Region region);
    void update(Region region);
    void delete(Long id);
}
