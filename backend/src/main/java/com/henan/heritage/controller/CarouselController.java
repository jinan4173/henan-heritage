package com.henan.heritage.controller;

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
    public Map<String, Object> list() {
        List<Carousel> carouselList = carouselService.list();
        return Map.of("success", true, "data", carouselList);
    }
    
    /**
     * 获取启用的轮播图列表（前端展示使用）
     * @return 启用的轮播图列表
     */
    @GetMapping("/enabled-list")
    public Map<String, Object> getEnabledList() {
        List<Carousel> enabledList = carouselService.getEnabledList();
        return Map.of("success", true, "data", enabledList);
    }
    
    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    @GetMapping("/get/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Carousel carousel = carouselService.getById(id);
        return Map.of("success", true, "data", carousel);
    }
    
    /**
     * 添加轮播图
     * @param carousel 轮播图对象
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Carousel carousel) {
        boolean success = carouselService.add(carousel);
        return Map.of("success", success);
    }
    
    /**
     * 更新轮播图
     * @param carousel 轮播图对象
     * @return 是否更新成功
     */
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Carousel carousel) {
        boolean success = carouselService.update(carousel);
        return Map.of("success", success);
    }
    
    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = carouselService.delete(id);
        return Map.of("success", success);
    }
}
