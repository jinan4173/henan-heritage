package com.henan.heritage.controller;

import com.henan.heritage.entity.HeritageImage;
import com.henan.heritage.service.HeritageImageService;
import com.henan.heritage.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class HeritageImageController {

    @Autowired
    private HeritageImageService heritageImageService;

    @GetMapping("/list/{heritageId}")
    public Map<String, Object> listByHeritageId(@PathVariable Long heritageId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<HeritageImage> images = heritageImageService.listByHeritageId(heritageId);
            result.put("success", true);
            result.put("data", images);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            String filePath = FileUploadUtils.uploadFile(file);
            result.put("success", true);
            result.put("filePath", filePath);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody HeritageImage image) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageImageService.save(image);
            result.put("success", true);
            result.put("message", "保存成功");
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
            heritageImageService.delete(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/deleteByHeritageId/{heritageId}")
    public Map<String, Object> deleteByHeritageId(@PathVariable Long heritageId) {
        Map<String, Object> result = new HashMap<>();
        try {
            heritageImageService.deleteByHeritageId(heritageId);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}