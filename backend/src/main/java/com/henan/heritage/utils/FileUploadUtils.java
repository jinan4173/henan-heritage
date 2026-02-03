package com.henan.heritage.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtils {
    private static final String UPLOAD_DIR = "D:\\henan-heritage\\uploads";

    static {
        // 创建上传目录
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建文件
        File dest = new File(UPLOAD_DIR + File.separator + fileName);
        file.transferTo(dest);

        // 返回文件相对路径
        return "/uploads/" + fileName;
    }

    public static boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        // 构建完整文件路径
        String fullPath = UPLOAD_DIR + File.separator + filePath.substring(8); // 去掉 "/uploads/"
        File file = new File(fullPath);
        return file.exists() && file.delete();
    }
}