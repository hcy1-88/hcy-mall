package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/26 15:56
 */
@SpringBootApplication
@MapperScan("com.hcy.mapper")
public class ManageApp {
    public static void main(String[] args) {
        SpringApplication.run(ManageApp.class,args);
    }
}
