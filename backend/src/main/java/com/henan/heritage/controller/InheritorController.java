package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.Inheritor;
import com.henan.heritage.service.InheritorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/inheritor", produces = "application/json; charset=UTF-8")
public class InheritorController {

    @Autowired
    private InheritorService inheritorService;

    /**
     * 获取传承人列表
     * @param page 页码
     * @param pageSize 每页大小
     * @return 传承人列表
     */
    @GetMapping("/list")
    public Result<List<Inheritor>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Inheritor> inheritors = inheritorService.listAll();
            int total = inheritors.size();
            List<Inheritor> pageList = new ArrayList<>();
            if (total > 0) {
                int start = (page - 1) * pageSize;
                if (start < total) {
                    int end = Math.min(start + pageSize, total);
                    pageList = new ArrayList<>(inheritors.subList(start, end));
                }
            }
            return Result.success(pageList, total);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 根据ID获取传承人详情
     * @param id 传承人ID
     * @return 传承人详情
     */
    @GetMapping("/get/{id}")
    public Result<Inheritor> getById(@PathVariable Long id) {
        try {
            Inheritor inheritor = inheritorService.getById(id);
            return Result.success(inheritor);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 保存传承人信息
     * @param inheritor 传承人信息
     * @return 保存结果
     */
    @PostMapping("/save")
    public Result<Long> save(@RequestBody Inheritor inheritor) {
        try {
            inheritorService.save(inheritor);
            return Result.success(inheritor.getId());
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 更新传承人信息
     * @param inheritor 传承人信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody Inheritor inheritor) {
        try {
            inheritorService.update(inheritor);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 删除传承人
     * @param id 传承人ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            inheritorService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}
