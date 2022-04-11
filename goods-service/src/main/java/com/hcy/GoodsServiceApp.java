package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/26 16:05
 */
@SpringBootApplication
@MapperScan("com.hcy.mapper")
public class GoodsServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsServiceApp.class,args);
    }
}
