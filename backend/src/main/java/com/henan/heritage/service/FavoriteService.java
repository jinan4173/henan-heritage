package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageFavorite;
import java.util.List;

public interface FavoriteService {
    /**
     * 根据用户ID获取收藏列表
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<HeritageFavorite> listByUserId(Long userId);

    /**
     * 添加收藏
     * @param favorite 收藏信息
     * @return 收藏ID
     */
    Long add(HeritageFavorite favorite);

    /**
     * 删除收藏
     * @param id 收藏ID
     */
    void delete(Long id);

    /**
     * 检查是否已收藏
     * @param userId 用户ID
     * @param heritageId 非遗项目ID
     * @param mediaId 媒体资源ID
     * @return 是否已收藏
     */
    boolean checkFavorite(Long userId, Long heritageId, Long mediaId);

    /**
     * 获取所有收藏列表
     * @return 收藏列表
     */
    List<HeritageFavorite> listAll();

    /**
     * 批量删除收藏
     * @param ids 收藏ID列表
     */
    void batchDelete(List<Long> ids);
}
