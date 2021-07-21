package com.util.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean persist(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }

    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    public boolean deleteKeys(Collection<String> keys) {
        return redisTemplate.delete(keys) == keys.size();
    }

    public boolean deleteKeyLike(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Set<String> keys = redisTemplate.keys(key + "*");
        if (keys.isEmpty()) {
            return false;
        }
        return this.deleteKeys(keys);
    }

    public Object get(String key) {
        return null == key ? null : redisTemplate.boundValueOps(key).get();
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout) {
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
    }



}