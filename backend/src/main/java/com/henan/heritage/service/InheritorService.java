package com.henan.heritage.service;

import com.henan.heritage.entity.Inheritor;
import java.util.List;

public interface InheritorService {
    List<Inheritor> list();
    Inheritor getById(Long id);
    boolean create(Inheritor inheritor);
    boolean update(Inheritor inheritor);
    boolean delete(Long id);
    long count();
}
