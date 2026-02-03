package com.henan.heritage.controller;

import com.henan.heritage.service.HeritageItemService;
import com.henan.heritage.service.UserService;
import com.henan.heritage.service.InheritorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private HeritageItemService heritageItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private InheritorService inheritorService;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取各类数据的数量
            long heritageCount = heritageItemService.count();
            long userCount = userService.count();
            long inheritorCount = inheritorService.count();

            result.put("success", true);
            result.put("heritageCount", heritageCount);
            result.put("userCount", userCount);
            result.put("inheritorCount", inheritorCount);
            result.put("message", "获取统计数据成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取统计数据失败: " + e.getMessage());
        }
        return result;
    }
}
