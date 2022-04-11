package com.hcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/22 21:55
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.hcy.feign"})
public class PageServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PageServiceApp.class,args);
    }
}
