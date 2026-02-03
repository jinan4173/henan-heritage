package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageImage;
import java.util.List;

public interface HeritageImageService {
    List<HeritageImage> listByHeritageId(Long heritageId);
    void save(HeritageImage image);
    void delete(Long id);
    void deleteByHeritageId(Long heritageId);
}