package com.hcy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/3/9 19:50
 */
public class RedisUtil {

    public static Set<String> findKeyByPattern(String pattern, RedisTemplate redisTemplate){
        Set<String> set = (Set<String>) redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> result = new HashSet<>();
                //cursor用完要close，这里利用Java7新特性的自动close它
                try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build())){
                    while (cursor.hasNext()) {
                        byte[] bytes = cursor.next();
                        result.add(new String(bytes, StandardCharsets.UTF_8));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });
        return set;
    }
}
