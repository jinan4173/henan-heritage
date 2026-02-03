package com.henan.heritage.controller;

import com.henan.heritage.entity.HeritageItem;
import com.henan.heritage.entity.HeritageMedia;
import com.henan.heritage.entity.HeritageCategory;
import com.henan.heritage.entity.Region;
import com.henan.heritage.service.HeritageItemService;
import com.henan.heritage.service.HeritageCategoryService;
import com.henan.heritage.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/heritage", produces = "application/json; charset=UTF-8")
public class HeritageItemController {

    @Autowired
    private HeritageItemService heritageItemService;

    @Autowired
    private HeritageCategoryService heritageCategoryService;

    @Autowired
    private RegionService regionService;

    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<HeritageItem> items = heritageItemService.listAll(status);
            int total = items.size();
            List<HeritageItem> pageList = new ArrayList<>();
            if (total > 0) {
                int start = (page - 1) * pageSize;
                if (start < total) {
                    int end = Math.min(start + pageSize, total);
                    pageList = new ArrayList<>(items.subList(start, end));
                }
            }
            result.put("success", true);
            result.put("data", pageList);
            result.put("total", total);
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
            HeritageItem item = heritageItemService.getById(id);
            result.put("success", true);
            result.put("data", item);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/filter")
    public Map<String, Object> filter(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false, defaultValue = "1") Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<HeritageItem> items = heritageItemService.listByCategoryAndRegion(categoryId, regionId, status);
            result.put("success", true);
            result.put("data", items);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody HeritageItem item) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageItemService.save(item);
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
    public Map<String, Object> update(@RequestBody HeritageItem item) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageItemService.update(item);
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
            heritageItemService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/saveMedia")
    public Map<String, Object> saveMedia(@RequestBody HeritageMedia media) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageItemService.saveMedia(media);
            result.put("success", true);
            result.put("message", "保存媒体资源成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/deleteMediaByHeritageId/{heritageId}")
    public Map<String, Object> deleteMediaByHeritageId(@PathVariable Long heritageId) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageItemService.deleteMediaByHeritageId(heritageId);
            result.put("success", true);
            result.put("message", "删除媒体资源成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/categories")
    public Map<String, Object> getCategories() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<HeritageCategory> categories = heritageCategoryService.listAll();
            result.put("success", true);
            result.put("data", categories);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/regions")
    public Map<String, Object> getRegions() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Region> regions = regionService.listAll();
            result.put("success", true);
            result.put("data", regions);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "测试成功");
        result.put("data", "Hello, World!");
        System.out.println("测试接口被调用");
        return result;
    }
}