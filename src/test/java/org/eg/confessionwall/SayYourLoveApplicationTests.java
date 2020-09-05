package org.eg.confessionwall;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfessionWallApplication.class)
class SayYourLoveApplicationTests {
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
//
//    @Test
//    public void set(){
//        redisTemplate.opsForValue().set("myKey","myValue");
//        System.out.println(redisTemplate.opsForValue().get("myKey"));
//    }
}
