package com.henan.heritage.mapper;

import com.henan.heritage.entity.Inheritor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface InheritorMapper {
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
    Inheritor getById(@Param("id") Long id);

    /**
     * 插入传承人信息
     * @param inheritor 传承人信息
     */
    void insert(Inheritor inheritor);

    /**
     * 更新传承人信息
     * @param inheritor 传承人信息
     */
    void update(Inheritor inheritor);

    /**
     * 删除传承人
     * @param id 传承人ID
     */
    void delete(@Param("id") Long id);
}
