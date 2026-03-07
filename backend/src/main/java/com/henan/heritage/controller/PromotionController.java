package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.Promotion;
import com.henan.heritage.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // 新增宣传
    @PostMapping("/save")
    public Result save(@RequestBody Promotion promotion) {
        boolean success = promotionService.save(promotion);
        return success ? Result.success(success) : Result.error(500, "添加失败");
    }

    // 更新宣传
    @PutMapping("/update")
    public Result update(@RequestBody Promotion promotion) {
        boolean success = promotionService.update(promotion);
        return success ? Result.success(success) : Result.error(500, "更新失败");
    }

    // 删除宣传
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = promotionService.delete(id);
        return success ? Result.success(success) : Result.error(500, "删除失败");
    }

    // 批量删除宣传
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = promotionService.deleteBatch(ids);
        return success ? Result.success(success) : Result.error(500, "批量删除失败");
    }

    // 根据ID查询宣传
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id) {
        Promotion promotion = promotionService.getById(id);
        return Result.success(promotion);
    }

    // 查询所有宣传
    @GetMapping("/list")
    public Result list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        List<Promotion> promotions = promotionService.getByPage(page, limit, keyword);
        int total = promotionService.getCount(keyword);
        return Result.success(promotions, total);
    }

    // 查询宣传总数
    @GetMapping("/count")
    public Result count() {
        int count = promotionService.getCount("");
        return Result.success(count);
    }
}