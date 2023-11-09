package org.example.demo.service;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LockRegistryService {
    @Resource
    protected RedisLockRegistry redisLockRegistry;

    /**
     * 尝试一次加锁
     */
    @SneakyThrows
    public <T> Boolean tryLock(T lockKey, Long time) {
        return redisLockRegistry.obtain(lockKey).tryLock(time, TimeUnit.SECONDS);
    }

    /**
     * 重试机制多次加锁
     */
    @SneakyThrows
    public <T> Boolean reTryLock(T lockKey, Long time,int retryNum) {
        Boolean lockFlag = tryLock(lockKey, time);
        if (Boolean.TRUE.equals(lockFlag)) {
            return Boolean.TRUE;
        }
        for (int i = 0; i < retryNum; i++) {
            if (Boolean.TRUE.equals(tryLock(lockKey, time))){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 释放锁
     */
    public <T> void unlock(T lockKey) {
        redisLockRegistry.obtain(lockKey).unlock();
    }
}
