package com.henan.heritage.controller;

import com.henan.heritage.entity.News;
import com.henan.heritage.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/news", produces = "application/json; charset=UTF-8")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(required = false) Integer status) {
        try {
            // 从数据库获取数据
            System.out.println("从数据库获取新闻列表");
            List<News> newsList = newsService.listAll(status);
            return Map.of("success", true, "data", newsList);
        } catch (Exception e) {
            System.out.println("获取新闻列表失败: " + e.getMessage());
            return Map.of("success", false, "message", "获取新闻列表失败");
        }
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        try {
            News news = newsService.getById(id);
            if (news != null) {
                return Map.of("success", true, "data", news);
            } else {
                return Map.of("success", false, "message", "新闻不存在");
            }
        } catch (Exception e) {
            System.out.println("获取新闻失败: " + e.getMessage());
            return Map.of("success", false, "message", "获取新闻失败");
        }
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody News news) {
        try {
            newsService.save(news);
            return Map.of("success", true, "message", "保存成功");
        } catch (Exception e) {
            System.out.println("保存新闻失败: " + e.getMessage());
            return Map.of("success", false, "message", "保存新闻失败");
        }
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody News news) {
        try {
            newsService.update(news);
            return Map.of("success", true, "message", "更新成功");
        } catch (Exception e) {
            System.out.println("更新新闻失败: " + e.getMessage());
            return Map.of("success", false, "message", "更新新闻失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        try {
            newsService.delete(id);
            return Map.of("success", true, "message", "删除成功");
        } catch (Exception e) {
            System.out.println("删除新闻失败: " + e.getMessage());
            return Map.of("success", false, "message", "删除新闻失败");
        }
    }
}
