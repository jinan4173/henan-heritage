package com.henan.heritage.service;

import com.henan.heritage.entity.CultureNews;
import java.util.List;

public interface CultureNewsService {
    List<CultureNews> listAll(Integer type, Integer status);
    CultureNews getById(Long id);
    void save(CultureNews cultureNews);
    void update(CultureNews cultureNews);
    void delete(Long id);
    long count(Integer type, Integer status);
}
