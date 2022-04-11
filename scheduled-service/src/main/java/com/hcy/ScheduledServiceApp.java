package com.hcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.hcy.feign"})
@MapperScan("com.hcy.mapper")
@EnableScheduling
public class ScheduledServiceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ScheduledServiceApp.class,args);
    }
}
