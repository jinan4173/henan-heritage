package com.henan.heritage.mapper;

import com.henan.heritage.entity.Inheritor;
import java.util.List;

public interface InheritorMapper {
    List<Inheritor> selectList(Inheritor inheritor);
    Inheritor selectById(Long id);
    int insert(Inheritor inheritor);
    int update(Inheritor inheritor);
    int deleteById(Long id);
    long count();
}
