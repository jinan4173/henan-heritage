package com.henan.heritage.service;

import com.henan.heritage.entity.ActivityRegistration;
import java.util.List;

/**
 * 活动报名服务接口
 */
public interface ActivityRegistrationService {
    /**
     * 保存活动报名信息
     * @param registration 活动报名信息
     */
    void save(ActivityRegistration registration);

    /**
     * 根据ID查询活动报名信息
     * @param id 报名ID
     * @return 活动报名信息
     */
    ActivityRegistration getById(Long id);

    /**
     * 根据活动ID查询报名列表
     * @param activityId 活动ID
     * @return 报名列表
     */
    List<ActivityRegistration> getByActivityId(Long activityId);

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
    List<ActivityRegistration> listAll();
    
    /**
     * 根据活动ID删除所有相关的报名记录
     * @param activityId 活动ID
     */
    void deleteByActivityId(Long activityId);
    
    /**
     * 获取活动报名总数
     * @return 活动报名总数
     */
    long count();
}
