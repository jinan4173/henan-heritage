package com.henan.heritage.mapper;

import com.henan.heritage.entity.News;
import java.util.List;

public interface NewsMapper {
    List<News> selectList(Integer status);
    News selectById(Long id);
    void insert(News news);
    void update(News news);
    void delete(Long id);
    long count();
}
