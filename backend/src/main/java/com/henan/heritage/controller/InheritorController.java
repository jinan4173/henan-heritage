package com.henan.heritage.controller;

import com.henan.heritage.entity.Inheritor;
import com.henan.heritage.service.InheritorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inheritor", produces = "application/json; charset=UTF-8")
public class InheritorController {

    @Autowired
    private InheritorService inheritorService;

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取完整列表以计算总记录数
            List<Inheritor> allInheritors = inheritorService.list();
            int total = allInheritors.size();
            
            // 计算分页参数
            List<Inheritor> pageList = new ArrayList<>();
            if (total > 0) {
                int start = (page - 1) * pageSize;
                if (start < total) {
                    int end = Math.min(start + pageSize, total);
                    pageList = new ArrayList<>(allInheritors.subList(start, end));
                }
            }
            
            // 构建响应
            result.put("success", true);
            result.put("data", pageList);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取传承人列表失败: " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Inheritor inheritor = inheritorService.getById(id);
            if (inheritor != null) {
                result.put("success", true);
                result.put("data", inheritor);
            } else {
                result.put("success", false);
                result.put("message", "传承人不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取传承人详情失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody Inheritor inheritor) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = inheritorService.create(inheritor);
            if (success) {
                result.put("success", true);
                result.put("message", "保存传承人成功");
            } else {
                result.put("success", false);
                result.put("message", "保存传承人失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "保存传承人失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Inheritor inheritor) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = inheritorService.update(inheritor);
            if (success) {
                result.put("success", true);
                result.put("message", "更新传承人成功");
            } else {
                result.put("success", false);
                result.put("message", "更新传承人失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新传承人失败: " + e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = inheritorService.delete(id);
            if (success) {
                result.put("success", true);
                result.put("message", "删除传承人成功");
            } else {
                result.put("success", false);
                result.put("message", "删除传承人失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除传承人失败: " + e.getMessage());
        }
        return result;
    }
}
