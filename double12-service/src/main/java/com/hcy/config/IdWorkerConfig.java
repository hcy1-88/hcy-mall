package com.hcy.config;

import com.hcy.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/26 16:14
 */
@Configuration
public class IdWorkerConfig {
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);  // 机器id，序列号
    }
}
