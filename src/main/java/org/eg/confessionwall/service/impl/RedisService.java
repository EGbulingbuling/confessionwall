package org.eg.confessionwall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("RedisService")
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(int id,int value){
        Map<String,String> map=new HashMap<>();
        map.put(id+"",value+"");
        this.redisTemplate.opsForHash().putAll("MESSAGE_COUNT", map);
    }

    public void delete(int id){
        this.redisTemplate.opsForHash().delete("MESSAGE_COUNT", id+"");
    }

    public int get(int id) throws Exception{
        Object obj = this.redisTemplate.opsForHash().get("MESSAGE_COUNT", id+"");

        String messageCountStr=(String)obj;
        int messageCount=Integer.parseInt(messageCountStr);
        return messageCount;
    }
}
