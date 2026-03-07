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


/**
 * 数据库初始化工具类
 * 用于在应用启动时执行SQL脚本，添加初始数据
 */
@Component
public class DatabaseInitializer implements InitializingBean {

    static {
        System.out.println("DatabaseInitializer class loaded!");
    }

    public DatabaseInitializer() {
        System.out.println("DatabaseInitializer constructor called!");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 应用启动时执行数据库连接检查
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            System.out.println("========================================");
            System.out.println("开始数据库连接检查...");
            
            // 检查数据库连接
            System.out.println("检查数据库连接...");
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null) {
                System.out.println("数据库连接正常!");
            } else {
                System.out.println("数据库连接异常!");
            }
            
            // 验证数据库表是否存在
            System.out.println("验证数据库表是否存在...");
            Integer tableCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'heritage_item' AND table_schema = DATABASE()", Integer.class);
            if (tableCount != null && tableCount > 0) {
                System.out.println("heritage_item表存在!");
                // 验证表中是否有数据
                Integer heritageCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM heritage_item", Integer.class);
                if (heritageCount != null) {
                    System.out.println("heritage_item表中共有 " + heritageCount + " 条记录");
                } else {
                    System.out.println("heritage_item表记录数查询异常!");
                }
            } else {
                System.out.println("heritage_item表不存在，请确保数据库表结构已创建!");
            }
            
            System.out.println("数据库连接检查完成!");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("数据库连接检查失败: " + e.getMessage());
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
                // 跳过空行
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue;
                }
                
                // 跳过注释行（以 -- 开头的行）
                if (trimmedLine.startsWith("--")) {
                    continue;
                }
                
                // 处理行尾注释
                int commentIndex = trimmedLine.indexOf("--");
                if (commentIndex != -1) {
                    trimmedLine = trimmedLine.substring(0, commentIndex).trim();
                    if (trimmedLine.isEmpty()) {
                        continue;
                    }
                }
                
                currentStatement.append(trimmedLine).append(" ");
                
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