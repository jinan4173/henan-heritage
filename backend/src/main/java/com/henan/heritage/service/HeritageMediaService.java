package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageMedia;
import java.util.List;

public interface HeritageMediaService {
    /**
     * 根据非遗项目ID查询媒体资源列表
     * @param heritageItemId 非遗项目ID
     * @return 媒体资源列表
     */
    List<HeritageMedia> getByHeritageItemId(Long heritageItemId);

    /**
     * 根据ID查询媒体资源
     * @param id 媒体资源ID
     * @return 媒体资源
     */
    HeritageMedia getById(Long id);

    /**
     * 保存媒体资源
     * @param heritageMedia 媒体资源
     */
    void save(HeritageMedia heritageMedia);

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
