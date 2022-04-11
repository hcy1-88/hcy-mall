package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/10 11:45
 */
@SpringBootApplication
@MapperScan("com.hcy.mapper")
@EnableFeignClients(basePackages = {"com.hcy.feign"})
public class OrderServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class,args);
    }
}
