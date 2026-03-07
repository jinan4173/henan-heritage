package com.henan.heritage.service.impl;

import com.henan.heritage.entity.ActivityRegistration;
import com.henan.heritage.mapper.ActivityRegistrationMapper;
import com.henan.heritage.service.ActivityRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 活动报名服务实现类
 */
@Service
public class ActivityRegistrationServiceImpl implements ActivityRegistrationService {

    @Autowired
    private ActivityRegistrationMapper activityRegistrationMapper;

    @Override
    @Transactional
    public void save(ActivityRegistration registration) {
        activityRegistrationMapper.insert(registration);
    }

    @Override
    public ActivityRegistration getById(Long id) {
        return activityRegistrationMapper.selectById(id);
    }

    @Override
    public List<ActivityRegistration> getByActivityId(Long activityId) {
        return activityRegistrationMapper.selectByActivityId(activityId);
    }

    @Override
    @Transactional
    public void updateStatus(ActivityRegistration registration) {
        activityRegistrationMapper.updateStatus(registration);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        activityRegistrationMapper.delete(id);
    }

    @Override
    public List<ActivityRegistration> listAll() {
        return activityRegistrationMapper.selectAll();
    }

    @Override
    @Transactional
    public void deleteByActivityId(Long activityId) {
        System.out.println("根据活动ID删除报名记录，活动ID: " + activityId);
        activityRegistrationMapper.deleteByActivityId(activityId);
        System.out.println("根据活动ID删除报名记录成功");
    }
}
