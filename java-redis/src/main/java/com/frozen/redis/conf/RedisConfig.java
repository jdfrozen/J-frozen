package com.frozen.redis.conf;

        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import redis.clients.jedis.JedisPool;

/**
 * @Auther: Frozen
 * @Date: 2019/2/21 21:01
 * @Description:
 */
@Configuration
public class RedisConfig {
    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Value("${redis.host}") String host, @Value("${redis.port}") int port) {
        return new JedisPool(host,port);
    }
}
