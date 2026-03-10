package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
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
    public Result<List<CultureNews>> list(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        List<CultureNews> items = cultureNewsService.listAll(type, status);
        return Result.success(items, items.size());
    }

    @GetMapping("/get/{id}")
    public Result<CultureNews> getById(@PathVariable Long id) {
        CultureNews item = cultureNewsService.getById(id);
        return Result.success(item);
    }

    @PostMapping("/save")
    public Result<Long> save(@RequestBody CultureNews item) {
        cultureNewsService.save(item);
        return Result.success(item.getId());
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody CultureNews item) {
        cultureNewsService.update(item);
        return Result.success("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        cultureNewsService.delete(id);
        return Result.success("删除成功");
    }

    @GetMapping("/count")
    public Result<Long> count(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        long count = cultureNewsService.count(type, status);
        return Result.success(count);
    }

    @PostMapping("/batch-delete")
    public Result<String> batchDelete(@RequestBody Map<String, List<Long>> request) {
        List<Long> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return Result.badRequest("请选择要删除的记录");
        }
        cultureNewsService.batchDelete(ids);
        return Result.success("批量删除成功");
    }
}
