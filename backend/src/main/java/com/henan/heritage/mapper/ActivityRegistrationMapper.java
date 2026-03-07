package com.henan.heritage.mapper;

import com.henan.heritage.entity.ActivityRegistration;
import java.util.List;

/**
 * 活动报名Mapper
 */
public interface ActivityRegistrationMapper {
    /**
     * 插入活动报名信息
     * @param registration 活动报名信息
     */
    void insert(ActivityRegistration registration);

    /**
     * 根据ID查询活动报名信息
     * @param id 报名ID
     * @return 活动报名信息
     */
    ActivityRegistration selectById(Long id);

    /**
     * 根据活动ID查询报名列表
     * @param activityId 活动ID
     * @return 报名列表
     */
    List<ActivityRegistration> selectByActivityId(Long activityId);

    /**
     * 更新活动报名状态
     * @param registration 活动报名信息
     */
    void updateStatus(ActivityRegistration registration);

    /**
     * 删除活动报名信息
     * @param id 报名ID
     */
    void delete(Long id);

    /**
     * 查询所有活动报名信息
     * @return 活动报名列表
     */
    List<ActivityRegistration> selectAll();
    
    /**
     * 根据活动ID删除所有相关的报名记录
     * @param activityId 活动ID
     */
    void deleteByActivityId(Long activityId);
}
