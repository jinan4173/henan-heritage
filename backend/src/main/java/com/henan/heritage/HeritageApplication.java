package com.henan.heritage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.henan.heritage"})
public class HeritageApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeritageApplication.class, args);
    }
}