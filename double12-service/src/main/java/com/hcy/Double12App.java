package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 10:56
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.hcy.mapper")
@EnableFeignClients(basePackages = {"com.hcy.feign"})  // 参数是 feign接口的包名
public class Double12App {
    public static void main(String[] args) {
        SpringApplication.run(Double12App.class,args);
    }
}
