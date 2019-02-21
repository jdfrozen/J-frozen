package com.frozen.redis.data;

import com.frozen.redis.utils.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Auther: Frozen
 * @Date: 2019/2/21 21:17
 * @Description: String 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {
    @Resource
    RedisClient redisClient;

    @Test
    public void stringTest() throws Exception {
        String key = "name";
        String value = "frozen";
        redisClient.set(key,value);
        System.out.println("value="+redisClient.get(key));
    }
}
