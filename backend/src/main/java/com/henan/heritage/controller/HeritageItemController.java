package com.henan.heritage.controller;

import com.henan.heritage.common.Result;

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
import java.util.List;

@RestController
@RequestMapping(value = "/heritage", produces = "application/json; charset=UTF-8")
public class HeritageItemController {

    @Autowired
    private HeritageItemService heritageItemService;

    @Autowired
    private HeritageCategoryService heritageCategoryService;

    @Autowired
    private RegionService regionService;

    /**
     * 获取非遗项目列表
     * @param status 状态
     * @param page 页码
     * @param pageSize 每页大小
     * @return 非遗项目列表
     */
    @GetMapping("/list")
    public Result<List<HeritageItem>> list(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
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
            return Result.success(pageList, total);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 根据ID获取非遗项目详情
     * @param id 项目ID
     * @return 非遗项目详情
     */
    @GetMapping("/get/{id}")
    public Result<HeritageItem> getById(@PathVariable Long id) {
        try {
            HeritageItem item = heritageItemService.getById(id);
            return Result.success(item);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 根据分类和地区筛选非遗项目
     * @param categoryId 分类ID
     * @param regionId 地区ID
     * @param status 状态
     * @param page 页码
     * @param pageSize 每页大小
     * @return 筛选后的非遗项目列表
     */
    @GetMapping("/filter")
    public Result<List<HeritageItem>> filter(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false, defaultValue = "1") Integer status,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        try {
            List<HeritageItem> items = heritageItemService.listByCategoryAndRegion(categoryId, regionId, status);
            
            // 计算分页
            int total = items.size();
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, total);
            
            List<HeritageItem> paginatedItems;
            if (start >= total) {
                paginatedItems = new ArrayList<>();
            } else {
                paginatedItems = items.subList(start, end);
            }
            
            return Result.success(paginatedItems, total);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 保存非遗项目
     * @param item 非遗项目信息
     * @return 保存结果
     */
    @PostMapping("/save")
    public Result<Long> save(@RequestBody HeritageItem item) {
        try {
            heritageItemService.save(item);
            return Result.success(item.getId());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 更新非遗项目
     * @param item 非遗项目信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody HeritageItem item) {
        try {
            heritageItemService.update(item);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 删除非遗项目
     * @param id 项目ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            heritageItemService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 保存媒体资源
     * @param media 媒体资源信息
     * @return 保存结果
     */
    @PostMapping("/saveMedia")
    public Result<String> saveMedia(@RequestBody HeritageMedia media) {
        try {
            heritageItemService.saveMedia(media);
            return Result.success("保存媒体资源成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 根据项目ID删除媒体资源
     * @param heritageId 项目ID
     * @return 删除结果
     */
    @DeleteMapping("/deleteMediaByHeritageId/{heritageId}")
    public Result<String> deleteMediaByHeritageId(@PathVariable Long heritageId) {
        try {
            heritageItemService.deleteMediaByHeritageId(heritageId);
            return Result.success("删除媒体资源成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取非遗项目分类列表
     * @return 分类列表
     */
    @GetMapping("/categories")
    public Result<List<HeritageCategory>> getCategories() {
        try {
            List<HeritageCategory> categories = heritageCategoryService.listAll();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取地区列表
     * @return 地区列表
     */
    @GetMapping("/regions")
    public Result<List<Region>> getRegions() {
        try {
            List<Region> regions = regionService.listAll();
            return Result.success(regions);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
    
    /**
     * 测试接口
     * @return 测试结果
     */
    @GetMapping("/test")
    public Result<String> test() {
        System.out.println("测试接口被调用");
        return Result.success("Hello, World!");
    }
}