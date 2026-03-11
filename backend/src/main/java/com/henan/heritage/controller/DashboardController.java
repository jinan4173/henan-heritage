package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.service.HeritageItemService;
import com.henan.heritage.service.UserService;
import com.henan.heritage.service.InheritorService;
import com.henan.heritage.service.HeritageCategoryService;
import com.henan.heritage.service.RegionService;
import com.henan.heritage.service.ActivityRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private HeritageItemService heritageItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private InheritorService inheritorService;

    @Autowired
    private HeritageCategoryService heritageCategoryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        try {
            // 获取各类数据的数量
            long heritageCount = heritageItemService.count();
            long userCount = userService.count();
            long inheritorCount = inheritorService.count();
            long activityCount = activityRegistrationService.count();

            Map<String, Object> data = new HashMap<>();
            data.put("heritageCount", heritageCount);
            data.put("userCount", userCount);
            data.put("inheritorCount", inheritorCount);
            data.put("activityCount", activityCount);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error(500, "获取统计数据失败: " + e.getMessage());
        }
    }

    @GetMapping("/category-stats")
    public Result<Map<String, Long>> getCategoryStats() {
        try {
            // 获取非遗项目分类统计
            Map<String, Long> categoryStats = heritageCategoryService.getCategoryStats();
            return Result.success(categoryStats);
        } catch (Exception e) {
            return Result.error(500, "获取分类统计数据失败: " + e.getMessage());
        }
    }

    @GetMapping("/region-stats")
    public Result<Map<String, Long>> getRegionStats() {
        try {
            // 获取非遗项目地区分布
            Map<String, Long> regionStats = regionService.getRegionStats();
            return Result.success(regionStats);
        } catch (Exception e) {
            return Result.error(500, "获取地区统计数据失败: " + e.getMessage());
        }
    }

    @GetMapping("/user-trend")
    public Result<Map<String, Long>> getUserTrend() {
        try {
            // 获取用户注册趋势（最近6个月）
            Map<String, Long> userTrend = userService.getUserRegistrationTrend();
            return Result.success(userTrend);
        } catch (Exception e) {
            return Result.error(500, "获取用户趋势数据失败: " + e.getMessage());
        }
    }

    @GetMapping("/popular-heritage")
    public Result<List<Map<String, Object>>> getPopularHeritage() {
        try {
            // 获取热门非遗项目排行
            List<Map<String, Object>> popularHeritage = heritageItemService.getPopularHeritage(10);
            return Result.success(popularHeritage);
        } catch (Exception e) {
            return Result.error(500, "获取热门非遗项目失败: " + e.getMessage());
        }
    }
}
