package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.entity.HeritageComment;
import com.henan.heritage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/comment", produces = "application/json; charset=UTF-8")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取非遗项目的评论列表
     * @param heritageId 非遗项目ID
     * @return 评论列表
     */
    @GetMapping("/listByHeritage")
    public Result<List<HeritageComment>> listByHeritage(@RequestParam Long heritageId) {
        try {
            List<HeritageComment> comments = commentService.listByHeritageId(heritageId);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取媒体资源的评论列表
     * @param mediaId 媒体资源ID
     * @return 评论列表
     */
    @GetMapping("/listByMedia")
    public Result<List<HeritageComment>> listByMedia(@RequestParam Long mediaId) {
        try {
            List<HeritageComment> comments = commentService.listByMediaId(mediaId);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public Result<Long> add(@RequestBody HeritageComment comment) {
        try {
            Long id = commentService.add(comment);
            return Result.success(id);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 删除评论
     * @param id 评论ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取用户的评论列表
     * @param userId 用户ID
     * @return 评论列表
     */
    @GetMapping("/listByUser")
    public Result<List<HeritageComment>> listByUser(@RequestParam Long userId) {
        try {
            List<HeritageComment> comments = commentService.listByUserId(userId);
            return Result.success(comments);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 获取所有评论列表（后台管理用）
     * @return 评论列表
     */
    @GetMapping("/list")
    public Result<List<HeritageComment>> list() {
        try {
            List<HeritageComment> comments = commentService.listAll();
            return Result.success(comments);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    /**
     * 批量删除评论
     * @param ids 评论ID列表
     * @return 删除结果
     */
    @DeleteMapping("/delete/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        try {
            commentService.batchDelete(ids);
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}
