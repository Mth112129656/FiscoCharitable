package org.example.demo.service;

import org.example.demo.entity.IdValue;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDataService {

    private static final Logger log = LoggerFactory.getLogger(RedisDataService.class) ;

    @Resource
    private RedisTemplate<String,Object> redisTemplate ;

    public void postdataString () throws Exception {
        // 1、添加K-V缓存
        redisTemplate.opsForValue().set("ok123",123456,300, TimeUnit.SECONDS);
    }

    public Object getdataString (String value) throws Exception {
//        JsonMapper jsonMapper = new JsonMapper() ;
//        String jsonVar = jsonMapper.writeValueAsString(new IdValue(1,"Json格式")) ;
//        redisTemplate.opsForValue().set("key-02",jsonVar,500,TimeUnit.SECONDS);

        Object key01 = redisTemplate.opsForValue().get("ok123");
        log.info("key01：{}",key01);
        return key01;
    }

    public void dataString () throws Exception {
        // 1、添加K-V缓存
        redisTemplate.opsForValue().set("key-01","value-01",300, TimeUnit.SECONDS);
        // 2、添加JSON格式
        JsonMapper jsonMapper = new JsonMapper() ;
        String jsonVar = jsonMapper.writeValueAsString(new IdValue(1,"Json格式")) ;
        redisTemplate.opsForValue().set("key-02",jsonVar,500,TimeUnit.SECONDS);

        Object key01 = redisTemplate.opsForValue().get("key-01");
        Object key02 = redisTemplate.opsForValue().get("key-02");
        log.info("key01：{},key02：{}",key01,key02);
    }

    public void dataList (){
        // 1、左侧写数据
        redisTemplate.opsForList().leftPushAll("data-list","value-01","value-02","value-03");
        // 2、右侧读数据
        Object rightPop01 = redisTemplate.opsForList().rightPop("data-list") ;
        Object rightPop02 = redisTemplate.opsForList().rightPop("data-list") ;
        log.info("rightPop01：{},rightPop02：{}",rightPop01,rightPop02);
    }

    public void dataSet (){
        // 1、写数据
        redisTemplate.opsForSet().add("data-set","Java","C++","Python","C++");
        // 2、读数据
        Object pop01 = redisTemplate.opsForSet().pop("data-set");
        Object pop02 = redisTemplate.opsForSet().pop("data-set");
        log.info("pop01：{},pop02：{}",pop01,pop02);
    }

    public void dataHash (){
        // 1、写数据
        HashMap<String,String> hashMap = new HashMap<>() ;
        hashMap.put("key1","value1") ;
        hashMap.put("key2","value2") ;
        redisTemplate.opsForHash().putAll("data-hash",hashMap);
        // 2、读数据
        Object kv1 = redisTemplate.opsForHash().get("data-hash","key1");
        Object kv2 = redisTemplate.opsForHash().get("data-hash","key2");
        log.info("kv1：{},kv2：{}",kv1,kv2);
    }

    public void dataSortedSet (){
        // 1、写数据
        redisTemplate.opsForZSet().add("sorted-set","Java",1.0);
        redisTemplate.opsForZSet().add("sorted-set","Python",3.0);
        redisTemplate.opsForZSet().add("sorted-set","C++",2.0);
        // 2、读数据
        Object popMax = redisTemplate.opsForZSet().popMax("sorted-set");
        Object popMin = redisTemplate.opsForZSet().popMin("sorted-set");
        log.info("popMax：{},popMin：{}",popMax,popMin);
    }
}
