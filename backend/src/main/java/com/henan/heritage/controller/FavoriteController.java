package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.HeritageFavorite;
import com.henan.heritage.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/favorite", produces = "application/json; charset=UTF-8")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 添加收藏
     * @param favorite 收藏信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public Result<Long> add(@RequestBody HeritageFavorite favorite) {
        try {
            Long id = favoriteService.add(favorite);
            return Result.success(id);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 取消收藏
     * @param id 收藏ID
     * @return 取消结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            favoriteService.delete(id);
            return Result.success("取消收藏成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 检查是否已收藏
     * @param userId 用户ID
     * @param heritageId 非遗项目ID
     * @param mediaId 媒体资源ID
     * @return 是否已收藏
     */
    @GetMapping("/check")
    public Result<Boolean> check(@RequestParam Long userId, @RequestParam(required = false) Long heritageId, @RequestParam(required = false) Long mediaId) {
        try {
            boolean isFavorite = favoriteService.checkFavorite(userId, heritageId, mediaId);
            return Result.success(isFavorite);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取用户的收藏列表
     * @param userId 用户ID
     * @return 收藏列表
     */
    @GetMapping("/listByUser")
    public Result<List<HeritageFavorite>> listByUser(@RequestParam Long userId) {
        try {
            List<HeritageFavorite> favorites = favoriteService.listByUserId(userId);
            return Result.success(favorites);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取所有收藏列表（后台管理用）
     * @return 收藏列表
     */
    @GetMapping("/list")
    public Result<List<HeritageFavorite>> list() {
        try {
            List<HeritageFavorite> favorites = favoriteService.listAll();
            return Result.success(favorites);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 批量删除收藏
     * @param ids 收藏ID列表
     * @return 删除结果
     */
    @DeleteMapping("/delete/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        try {
            favoriteService.batchDelete(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}
