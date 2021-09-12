package com.design.method.factory.secondKill.cache.redis;

import com.design.method.factory.secondKill.cache.SecCache;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component(value = "redisCache")
public class RedisCache<T> implements SecCache<T> {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final static Long expireTime = 6000L;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void update(String key,T t) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(t.getClass()));
        redisTemplate.opsForValue().set(key,t,expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String id,Class<T> type) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(type));
        redisTemplate.delete(id);
    }

    @Override
    public void insert(String key, T t) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(t.getClass()));
        redisTemplate.opsForValue().set(key,t,expireTime, TimeUnit.SECONDS);
    }

    @Override
    public RLock getLock(String lockey) {
        return redissonClient.getLock(lockey);
    }

    @Override
    public List<T> select(String productId, Class<T> type) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(type));
        return redisTemplate.opsForValue().get(productId)==null?null:Arrays.asList((T)redisTemplate.opsForValue().get(productId));
    }

}
