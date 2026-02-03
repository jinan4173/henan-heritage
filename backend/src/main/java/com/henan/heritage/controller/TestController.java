package com.henan.heritage.controller;

import com.henan.heritage.utils.DataImportTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 用于手动触发数据导入测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DataImportTest dataImportTest;

    /**
     * 触发数据导入测试
     * @return 测试结果
     */
    @GetMapping("/data-import")
    public String testDataImport() {
        dataImportTest.testDataImport();
        return "数据导入测试已执行，请查看控制台日志";
    }
}
