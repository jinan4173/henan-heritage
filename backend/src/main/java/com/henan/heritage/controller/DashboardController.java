package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
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
    public Result<Map<String, Object>> getStats() {
        try {
            // 获取各类数据的数量
            long heritageCount = heritageItemService.count();
            long userCount = userService.count();
            long inheritorCount = inheritorService.count();

            Map<String, Object> data = new HashMap<>();
            data.put("heritageCount", heritageCount);
            data.put("userCount", userCount);
            data.put("inheritorCount", inheritorCount);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error(500, "获取统计数据失败: " + e.getMessage());
        }
    }
}
