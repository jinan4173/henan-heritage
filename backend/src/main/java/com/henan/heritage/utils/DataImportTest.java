package com.henan.heritage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 数据导入测试类
 * 用于手动执行数据导入操作并查看详细错误信息
 */
@Component
public class DataImportTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 手动执行数据导入测试
     */
    public void testDataImport() {
        try {
            System.out.println("========================================");
            System.out.println("开始数据导入测试...");
            
            // 检查数据库连接
            System.out.println("检查数据库连接...");
            int count = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            System.out.println("数据库连接正常!");
            
            // 清空heritage_item表数据
            System.out.println("清空heritage_item表数据...");
            int deleteCount = jdbcTemplate.update("DELETE FROM heritage_item");
            System.out.println("成功删除 " + deleteCount + " 条记录");
            
            // 验证清空结果
            int emptyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM heritage_item", Integer.class);
            System.out.println("清空后heritage_item表中有 " + emptyCount + " 条记录");
            
            // 尝试插入一条测试数据
            System.out.println("插入测试数据...");
            String testSql = "INSERT INTO heritage_item (id, serial_number, project_code, title, category_name, declaration_region, protection_unit, category_id, region_id, status, create_time, update_time) VALUES (1, 1, '1', '测试项目', '民俗', '测试地区', '测试保护单位', 1, 1, 1, NOW(), NOW())";
            int insertCount = jdbcTemplate.update(testSql);
            System.out.println("成功插入 " + insertCount + " 条测试数据");
            
            // 验证插入结果
            int testCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM heritage_item", Integer.class);
            System.out.println("插入后heritage_item表中有 " + testCount + " 条记录");
            
            System.out.println("数据导入测试完成!");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("数据导入测试失败: " + e.getMessage());
            e.printStackTrace();
            System.err.println("========================================");
        }
    }
}
