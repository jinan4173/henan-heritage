package com.henan.heritage.mapper;

import com.henan.heritage.entity.CultureNews;
import java.util.List;

public interface CultureNewsMapper {
    List<CultureNews> selectList(Integer type, Integer status);
    CultureNews selectById(Long id);
    void insert(CultureNews cultureNews);
    void update(CultureNews cultureNews);
    void delete(Long id);
    long count(Integer type, Integer status);
}
