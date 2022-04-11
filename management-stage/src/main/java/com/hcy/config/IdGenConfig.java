package com.hcy.config;

import com.hcy.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/27 5:24
 */
@Configuration
public class IdGenConfig {
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
