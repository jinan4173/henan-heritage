package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageImage;
import java.util.List;

public interface HeritageImageMapper {
    List<HeritageImage> selectByHeritageId(Long heritageId);
    HeritageImage selectById(Long id);
    void insert(HeritageImage image);
    void update(HeritageImage image);
    void delete(Long id);
}