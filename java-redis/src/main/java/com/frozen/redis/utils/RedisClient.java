package com.frozen.redis.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @Auther: Frozen
 * @Date: 2019/2/21 21:09
 * @Description:
 */
@Component
public class RedisClient {
    @Resource(name = "jedisPool")
    JedisPool jedisPool;

    /**
     * 设置缓存
     * @param key
     * @param value
     * @throws Exception
     */
    public void set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return  jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
