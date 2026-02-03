package com.henan.heritage.mapper;

import com.henan.heritage.entity.HeritageMedia;
import java.util.List;

public interface HeritageMediaMapper {
    /**
     * 根据非遗项目ID查询媒体资源列表
     * @param heritageItemId 非遗项目ID
     * @return 媒体资源列表
     */
    List<HeritageMedia> selectByHeritageItemId(Long heritageItemId);

    /**
     * 根据ID查询媒体资源
     * @param id 媒体资源ID
     * @return 媒体资源
     */
    HeritageMedia selectById(Long id);

    /**
     * 插入媒体资源
     * @param heritageMedia 媒体资源
     */
    void insert(HeritageMedia heritageMedia);

    /**
     * 更新媒体资源
     * @param heritageMedia 媒体资源
     */
    void update(HeritageMedia heritageMedia);

    /**
     * 删除媒体资源
     * @param id 媒体资源ID
     */
    void delete(Long id);

    /**
     * 根据非遗项目ID删除媒体资源
     * @param heritageItemId 非遗项目ID
     */
    void deleteByHeritageItemId(Long heritageItemId);
}
