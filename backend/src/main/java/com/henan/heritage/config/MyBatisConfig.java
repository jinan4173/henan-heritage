package com.henan.heritage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.henan.heritage.mapper")
public class MyBatisConfig {
}