package com.dhhan.customFramework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisTemplate<String,String> redisStringTemplate;

    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        return redisStringTemplate.opsForValue().get(key);
    }
}
