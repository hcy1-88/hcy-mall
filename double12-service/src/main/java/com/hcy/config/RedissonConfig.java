package com.hcy.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig{
    public static final String KEY_PREFIX = "BUY_SKU_ID_";
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        // 单机模式，还可支持 集群、主从复制、哨兵 等模式
        config.useSingleServer().setAddress("redis://192.168.1.131:6379").setPassword("root").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}