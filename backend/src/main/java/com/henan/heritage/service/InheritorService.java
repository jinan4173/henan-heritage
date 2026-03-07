package com.henan.heritage.service;

import com.henan.heritage.entity.Inheritor;
import java.util.List;

public interface InheritorService {
    /**
     * 获取所有传承人列表
     * @return 传承人列表
     */
    List<Inheritor> listAll();

    /**
     * 根据ID获取传承人详情
     * @param id 传承人ID
     * @return 传承人详情
     */
    Inheritor getById(Long id);

    /**
     * 保存传承人信息
     * @param inheritor 传承人信息
     */
    void save(Inheritor inheritor);

    /**
     * 更新传承人信息
     * @param inheritor 传承人信息
     */
    void update(Inheritor inheritor);

    /**
     * 删除传承人
     * @param id 传承人ID
     */
    void delete(Long id);

    /**
     * 获取传承人总数
     * @return 传承人总数
     */
    long count();
}
