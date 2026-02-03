package com.henan.heritage.service;

import com.henan.heritage.entity.News;
import java.util.List;

public interface NewsService {
    List<News> listAll(Integer status);
    News getById(Long id);
    void save(News news);
    void update(News news);
    void delete(Long id);
    long count();
}
