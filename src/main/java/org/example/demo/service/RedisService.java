package org.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断redis键是否 已过期/不存在
     *
     * @param key
     * @return
     */
    public boolean tokenTTL(String key) {
        Long ttl = redisTemplate.getExpire(key);
        if (ttl < 0)
            return false;
        return true;
    }

    /**
     * 存入redis
     *
     * @param key     email
     * @param value   验证码
     * @param minutes 过期时间(分钟)
     * @throws Exception 未存入，抛出异常
     */
    public void setRedis(String key, String value, Integer minutes) throws Exception {
        redisTemplate.opsForValue().set(key, value, 60 * minutes, TimeUnit.SECONDS);
    }

    /**
     * 删除redis键
     *
     * @param key
     * @return
     */
    public boolean deleteRedis(String key) {
        return redisTemplate.delete(key);
    }


    /**
     * 获取redis键对应的value
     *
     * @param key
     * @return 若key不存在返回null
     */
    public String getRedis(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
