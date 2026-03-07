package com.henan.heritage.service;

import com.henan.heritage.entity.HeritageComment;
import java.util.List;

public interface CommentService {
    /**
     * 根据非遗项目ID获取评论列表
     * @param heritageId 非遗项目ID
     * @return 评论列表
     */
    List<HeritageComment> listByHeritageId(Long heritageId);

    /**
     * 根据媒体资源ID获取评论列表
     * @param mediaId 媒体资源ID
     * @return 评论列表
     */
    List<HeritageComment> listByMediaId(Long mediaId);

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 评论ID
     */
    Long add(HeritageComment comment);

    /**
     * 删除评论
     * @param id 评论ID
     */
    void delete(Long id);

    /**
     * 根据用户ID获取评论列表
     * @param userId 用户ID
     * @return 评论列表
     */
    List<HeritageComment> listByUserId(Long userId);

    /**
     * 获取所有评论列表
     * @return 评论列表
     */
    List<HeritageComment> listAll();

    /**
     * 批量删除评论
     * @param ids 评论ID列表
     */
    void batchDelete(List<Long> ids);
}
