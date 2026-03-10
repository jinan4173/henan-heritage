package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
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
    public Result<List<HeritageImage>> listByHeritageId(@PathVariable Long heritageId) {
        List<HeritageImage> images = heritageImageService.listByHeritageId(heritageId);
        return Result.success(images);
    }

    @PostMapping("/upload")
    public Result<Map<String, String>> upload(@RequestParam MultipartFile file) {
        try {
            String filePath = FileUploadUtils.uploadFile(file);
            Map<String, String> data = new HashMap<>();
            data.put("filePath", filePath);
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError("文件上传失败: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody HeritageImage image) {
        heritageImageService.save(image);
        return Result.success("保存成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        heritageImageService.delete(id);
        return Result.success("删除成功");
    }

    @DeleteMapping("/deleteByHeritageId/{heritageId}")
    public Result<String> deleteByHeritageId(@PathVariable Long heritageId) {
        heritageImageService.deleteByHeritageId(heritageId);
        return Result.success("删除成功");
    }
}