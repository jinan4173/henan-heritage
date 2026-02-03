package com.henan.heritage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 数据库初始化工具类
 * 用于在应用启动时执行SQL脚本，添加初始数据
 */
@Component
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 应用启动时执行初始化
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            System.out.println("========================================");
            System.out.println("开始数据库初始化...");
            
            // 检查数据库连接
            System.out.println("检查数据库连接...");
            int count = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            System.out.println("数据库连接正常!");
            
            // 清空heritage_item表数据
            System.out.println("清空heritage_item表数据...");
            int deleteCount = jdbcTemplate.update("DELETE FROM heritage_item");
            System.out.println("成功删除 " + deleteCount + " 条记录");
            System.out.println("heritage_item表数据清空完成!");
            
            // 执行表结构创建脚本
            System.out.println("执行表结构创建脚本...");
            executeSqlScript("classpath:data/create_tables.sql");
            
            // 执行初始数据脚本
            System.out.println("执行初始数据脚本...");
            executeSqlScript("classpath:data/init_data.sql");
            
            // 执行更新数据脚本
            System.out.println("执行更新数据脚本...");
            executeSqlScript("classpath:data/update_data.sql");
            
            // 执行导入数据脚本
            System.out.println("执行导入数据脚本...");
            executeSqlScript("classpath:data/import_heritage_items.sql");
            
            // 验证导入结果
            System.out.println("验证导入结果...");
            int heritageCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM heritage_item", Integer.class);
            System.out.println("heritage_item表中共有 " + heritageCount + " 条记录");
            
            System.out.println("数据库初始化完成!");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
            System.err.println("========================================");
        }
    }

    /**
     * 执行SQL脚本文件
     * @param scriptPath 脚本文件路径
     * @throws IOException IO异常
     * @throws SQLException SQL异常
     */
    private void executeSqlScript(String scriptPath) throws IOException, SQLException {
        Resource resource = resourceLoader.getResource(scriptPath);
        if (!resource.exists()) {
            throw new IOException("SQL脚本文件不存在: " + scriptPath);
        }

        System.out.println("读取SQL脚本文件: " + scriptPath);

        // 读取SQL脚本内容
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            StringBuilder currentStatement = new StringBuilder();
            int statementCount = 0;
            
            while ((line = reader.readLine()) != null) {
                // 跳过注释行和空行
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("--")) {
                    continue;
                }
                
                currentStatement.append(line).append("\n");
                
                // 如果遇到分号，执行当前语句
                if (trimmedLine.endsWith(";")) {
                    String statement = currentStatement.toString().trim();
                    statementCount++;
                    
                    try {
                        System.out.println("执行SQL语句 " + statementCount + ": " + statement.substring(0, Math.min(100, statement.length())) + (statement.length() > 100 ? "..." : ""));
                        jdbcTemplate.execute(statement);
                        System.out.println("SQL语句 " + statementCount + " 执行成功");
                    } catch (Exception e) {
                        System.err.println("执行SQL语句 " + statementCount + " 失败: " + statement);
                        System.err.println("错误信息: " + e.getMessage());
                        // 继续执行其他语句
                    }
                    
                    // 重置当前语句
                    currentStatement.setLength(0);
                }
            }
            
            System.out.println("SQL脚本执行完成，共执行了 " + statementCount + " 条语句");
        }
    }
}