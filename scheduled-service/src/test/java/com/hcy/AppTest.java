package com.hcy;

import static org.junit.Assert.assertTrue;

import com.hcy.service.ScheduledService;
import com.hcy.service.impl.ScheduledServiceImpl;
import com.hcy.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class AppTest 
{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test01(){
        String id = "2";
        Set<String> keyByPattern = RedisUtil.findKeyByPattern("CouponBeGrabbed::userid_*_goodsId_" + id,redisTemplate);
        System.out.println(keyByPattern);
    }
}
