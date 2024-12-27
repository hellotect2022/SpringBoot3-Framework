package com.dhhan.customFramework.redis;

import com.dhhan.customFramework.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RedisRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, JsonHelper.parseObjectToJson(value));
    }

    public <T> Optional<T> get(String key, Class<T> classType) {

        String jsonData = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(jsonData)) {
            return Optional.ofNullable(JsonHelper.convertToClass(jsonData,classType));
        }
        return Optional.empty();
    }

    /* mget (multiGet )*/
    public <T> List<T> mget(Collection<String> keys, Class<T> classType) {
        return redisTemplate.opsForValue().multiGet(keys).stream().map(value ->{
            return JsonHelper.convertToClass((String) value,classType);
        }).collect(Collectors.toList());
    }

    public <T> void hset(String key, String hashKey ,T value) {
        redisTemplate.opsForHash().put(key,hashKey,JsonHelper.parseObjectToJson(value));
    }

    public <T> void hsetAll(String key,T value) {
        redisTemplate.opsForHash().putAll(key,JsonHelper.getINSTANCE().convertValue(value, HashMap.class));
    }

    public <T> Optional<T> hget(String key, String hashKey, Class<T> classType) {
        String jsonData = (String) redisTemplate.opsForHash().get(key,hashKey);
        if (StringUtils.hasText(jsonData)) {
            return Optional.ofNullable(JsonHelper.convertToClass(jsonData,classType));
        }
        return Optional.empty();
    }

    public <T> List<T> hmget(String key, Collection hashKeys, Class<T> classType) {
        return (ArrayList<T>)redisTemplate.opsForHash().multiGet(key, hashKeys).stream()
                .map(obj->JsonHelper.convertToClass((String)obj, classType))
                .collect(Collectors.toList());
    }


    public void sadd(String key, Object... value){
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public <T> void lpush(String key, T value) {
        redisTemplate.opsForList().leftPush(key, JsonHelper.parseObjectToJson(value));
    }

    public <T> List<T> lrange(String key, long start, long end, Class<T> classType) {
        return redisTemplate.opsForList().range(key, start,end).stream()
                .map(obj->JsonHelper.convertToClass((String)obj, classType))
                .collect(Collectors.toList());
    }

    public <T> Optional<T> lpop(String key, Class<T> classType) {
        String jsonData = (String) redisTemplate.opsForList().leftPop(key);
        if (StringUtils.hasText(jsonData)) {
            return Optional.ofNullable(JsonHelper.convertToClass(jsonData,classType));
        }
        return Optional.empty();
    }

    public void rpush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // Sorted SET 저장
    public void zadd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, JsonHelper.parseObjectToJson(value), score);
    }

    public Set zrange(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key,start, end);
    }

    // basic String
    public Optional<String> hget(String key, String hashKey) {
        return hget(key, hashKey, String.class);
    }
    public Optional<String> get(String key) {
        return get(key, String.class);
    }
}
