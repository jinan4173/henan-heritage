package com.henan.heritage;

import com.henan.heritage.entity.User;
import com.henan.heritage.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.henan.heritage"})
public class HeritageApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeritageApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner run(UserService userService) {
        return args -> {
            // 检查用户表是否已有数据
            long count = userService.count();
            if (count < 10) {
                // 生成用户数据
                String[] names = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十", "郑一", "王二"};
                String[] emails = {
                    "zhangsan@example.com", "lisi@example.com", "wangwu@example.com", 
                    "zhaoliu@example.com", "qianqi@example.com", "sunba@example.com", 
                    "zhoujiu@example.com", "wushi@example.com", "zhengyi@example.com", "wanger@example.com"
                };
                
                for (int i = 0; i < 10; i++) {
                    User user = new User();
                    user.setUsername(names[i]);
                    user.setPassword("123456"); // 简单密码，实际项目中应该加密
                    user.setEmail(emails[i]);
                    user.setIsAdmin(0); // 普通用户
                    user.setStatus(1); // 启用状态
                    
                    try {
                        userService.save(user);
                        System.out.println("添加用户成功: " + user.getUsername());
                    } catch (Exception e) {
                        System.out.println("添加用户失败: " + e.getMessage());
                    }
                }
                
                System.out.println("用户添加完成！");
            } else {
                System.out.println("用户表已有足够数据，跳过添加操作。");
            }
        };
    }
}