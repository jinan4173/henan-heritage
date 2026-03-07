package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.ActivityRegistration;
import com.henan.heritage.service.ActivityRegistrationService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 活动报名控制器
 */
@RestController
@RequestMapping("/activity/registration")
public class ActivityRegistrationController {

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    /**
     * 提交活动报名
     */
    @PostMapping("/add")
    public String addRegistration(@RequestBody ActivityRegistration registration) {
        try {
            // 设置默认状态为1（待处理）
            if (registration.getStatus() == null) {
                registration.setStatus(1);
            }
            activityRegistrationService.save(registration);
            return "报名成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "报名失败: " + e.getMessage();
        }
    }

    /**
     * 根据活动ID查询报名列表
     */
    @GetMapping("/list")
    public List<ActivityRegistration> getRegistrationsByActivityId(@RequestParam Long activityId) {
        return activityRegistrationService.getByActivityId(activityId);
    }

    /**
     * 更新报名状态
     */
    @PutMapping("/update")
    public String updateRegistrationStatus(@RequestBody ActivityRegistration registration) {
        try {
            activityRegistrationService.updateStatus(registration);
            return "状态更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "状态更新失败: " + e.getMessage();
        }
    }

    /**
     * 删除报名
     */
    @DeleteMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable Long id) {
        try {
            activityRegistrationService.delete(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败: " + e.getMessage();
        }
    }

    /**
     * 查询所有报名
     */
    @GetMapping("/all")
    public Result<List<ActivityRegistration>> getAllRegistrations() {
        return Result.success(activityRegistrationService.listAll());
    }
}
