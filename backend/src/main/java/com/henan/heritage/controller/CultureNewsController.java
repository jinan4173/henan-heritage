package com.henan.heritage.controller;

import com.henan.heritage.entity.CultureNews;
import com.henan.heritage.service.CultureNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/culture-news", produces = "application/json; charset=UTF-8")
public class CultureNewsController {

    @Autowired
    private CultureNewsService cultureNewsService;

    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CultureNews> items = cultureNewsService.listAll(type, status);
            result.put("success", true);
            result.put("data", items);
            result.put("total", items.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            CultureNews item = cultureNewsService.getById(id);
            result.put("success", true);
            result.put("data", item);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody CultureNews item) {
        Map<String, Object> result = new HashMap<>();
        try {
            cultureNewsService.save(item);
            result.put("success", true);
            result.put("message", "保存成功");
            result.put("id", item.getId());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody CultureNews item) {
        Map<String, Object> result = new HashMap<>();
        try {
            cultureNewsService.update(item);
            result.put("success", true);
            result.put("message", "更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            cultureNewsService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/count")
    public Map<String, Object> count(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            long count = cultureNewsService.count(type, status);
            result.put("success", true);
            result.put("data", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/batch-delete")
    public Map<String, Object> batchDelete(@RequestBody Map<String, List<Long>> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Long> ids = request.get("ids");
            if (ids != null && !ids.isEmpty()) {
                cultureNewsService.batchDelete(ids);
                result.put("success", true);
                result.put("message", "批量删除成功");
            } else {
                result.put("success", false);
                result.put("message", "请选择要删除的记录");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
