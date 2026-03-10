package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.Carousel;
import com.henan.heritage.service.CarouselService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * 轮播图Controller
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    
    @Autowired
    private CarouselService carouselService;
    
    /**
     * 获取轮播图列表（管理后台使用）
     * @return 轮播图列表
     */
    @GetMapping("/list")
    public Result<List<Carousel>> list() {
        List<Carousel> carouselList = carouselService.list();
        return Result.success(carouselList);
    }
    
    /**
     * 获取启用的轮播图列表（前端展示使用）
     * @return 启用的轮播图列表
     */
    @GetMapping("/enabled-list")
    public Result<List<Carousel>> getEnabledList() {
        List<Carousel> enabledList = carouselService.getEnabledList();
        return Result.success(enabledList);
    }
    
    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    @GetMapping("/get/{id}")
    public Result<Carousel> getById(@PathVariable Long id) {
        Carousel carousel = carouselService.getById(id);
        return Result.success(carousel);
    }
    
    /**
     * 添加轮播图
     * @param carousel 轮播图对象
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Carousel carousel) {
        boolean success = carouselService.add(carousel);
        return success ? Result.success(Boolean.TRUE) : Result.error(500, "添加轮播图失败");
    }
    
    /**
     * 更新轮播图
     * @param carousel 轮播图对象
     * @return 是否更新成功
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Carousel carousel) {
        boolean success = carouselService.update(carousel);
        return success ? Result.success(Boolean.TRUE) : Result.error(500, "更新轮播图失败");
    }
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = carouselService.delete(id);
        return success ? Result.success(Boolean.TRUE) : Result.error(500, "删除轮播图失败");
    }
}
