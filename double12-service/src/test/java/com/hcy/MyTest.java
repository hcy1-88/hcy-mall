package com.hcy;

import com.hcy.dto.GoodsForDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 11:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class MyTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    public void testRedis(){
        ValueOperations<String, Object> sop = redisTemplate.opsForValue();
        List<GoodsForDetail> list = (List<GoodsForDetail>) sop.get("HotGoods::hot_goods_1465865873104441344");
        list.forEach(System.out::println);
    }

    @Test
    public void testList(){
        ListOperations<String, Object> opl = redisTemplate.opsForList();
        opl.rightPush("name","mike","bob");
        List<Object> name = opl.range("name", 0, -1);
        name.forEach(str -> {
            String ss = (String) str;
            System.out.println(ss);
        });
    }
}
