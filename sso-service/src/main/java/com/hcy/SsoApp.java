package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/1/29 17:57
 */
@SpringBootApplication
@MapperScan("com.hcy.mapper")
public class SsoApp {
    public static void main(String[] args) {
        SpringApplication.run(SsoApp.class,args);
    }
}
