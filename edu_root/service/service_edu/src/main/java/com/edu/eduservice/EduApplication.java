package com.edu.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.edu.eduservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
// 使得可以扫描其他module下面的com.edu包
@ComponentScan(basePackages = {"com.edu"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}
