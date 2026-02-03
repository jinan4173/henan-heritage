package com.henan.heritage.controller;

import com.henan.heritage.service.HeritageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private HeritageItemService heritageItemService;

    @GetMapping("/city")
    public Map<String, Object> cityStatistics() {
        try {
            List<Map<String, Object>> statistics = heritageItemService.getCityStatistics();
            return Map.of("success", true, "data", statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "获取地市统计数据失败: " + e.getMessage());
        }
    }

    @GetMapping("/category")
    public Map<String, Object> categoryStatistics() {
        try {
            List<Map<String, Object>> statistics = heritageItemService.getCategoryStatistics();
            return Map.of("success", true, "data", statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "获取分类统计数据失败: " + e.getMessage());
        }
    }
}
