package org.example.demo.common;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MybatisCache implements Cache{
    private static final Logger log = LoggerFactory.getLogger(MybatisCache.class);

    private RedisTemplate<String,Object> redisTemplate ;

    /**
     * ID：com.boot.redis.mapper.ArticleMapper
     */
    private String id;

    public MybatisCache (String id){
        log.info("mybatis...cache...id：{}",id);
        this.id = id ;
    }

    public RedisTemplate<String,Object> getRedisTemplate(){
        if(redisTemplate == null) {
            synchronized (MybatisCache.class) {
                if(redisTemplate == null) {
                    redisTemplate = SpringContextUtil.getBean("redisTemplate",RedisTemplate.class);
                    return redisTemplate;
                }
                return redisTemplate;
            }
        }
        return redisTemplate ;
    }
    @Override
    public String getId() {
        return this.id ;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.info("mybatis...cache...put...key：{},value：{}",key,value);
        this.getRedisTemplate().opsForValue().set(String.valueOf(key),value,5, TimeUnit.MINUTES);
    }

    @Override
    public Object getObject(Object key) {
        log.info("mybatis...cache...get...key：{}",key);
        return this.getRedisTemplate().opsForValue().get(String.valueOf(key)) ;
    }

    @Override
    public Object removeObject(Object key) {
        log.info("mybatis...cache...remove...key：{}",key);
        return this.getRedisTemplate().delete(String.valueOf(key));
    }

    @Override
    public void clear() {
        Set<String> keys = this.getRedisTemplate().keys("*" + id + "*");
        if (keys != null && keys.size()>0){
            log.info("mybatis...cache...clear...keys：{}",keys);
            this.getRedisTemplate().delete(keys) ;
        }
    }

    @Override
    public int getSize() {
        Set<String> keys = this.getRedisTemplate().keys("*" + id + "*");
        if (keys != null){
            log.info("mybatis...cache...size...keys：{}",keys.size());
            return keys.size() ;
        }
        return 0;
    }
}
