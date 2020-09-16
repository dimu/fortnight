package dimu.res.config.redis;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 配置信息,spring boot auto configure: {@link RedisConnectionFactory}, 
 * {@link RedisTemplate}, {@link StringRedisTemplate}
 * @author dwx
 *
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties propertis;
    
    /**
     * key-value all String type
     * @return RedisTemplate<String,String> bean
     */
//    @Bean
//    public RedisTemplate<String, String> stringRedisTemplate() {
//        RedisTemplate<String, String> tempalte = new RedisTemplate<>();
//
//        RedisSerializer<String> serializer = new StringRedisSerializer();
//        tempalte.setKeySerializer(serializer);
//        tempalte.setValueSerializer(serializer);
//
//        return tempalte;
//    }
    
    @Bean
    @ConditionalOnClass(JedisPool.class)
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig  = new JedisPoolConfig();
      
        Pool pool = propertis.getJedis().getPool();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMinIdle(pool.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait().getSeconds() * 1000);

        return new JedisPool(jedisPoolConfig, propertis.getHost());
    }
    
}
