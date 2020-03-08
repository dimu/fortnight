package dwx.res.redis.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicRedisApplicationTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

    }

    @Test
    public void setString() {
        redisTemplate.opsForValue().set("name", "dwx");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    public void setHash() {
        redisTemplate.opsForHash().put("person", "age", 30);
        redisTemplate.opsForHash().put("person", "sex", "female");
        System.out.println(redisTemplate.opsForHash().get("person", "age"));
        System.out.println(redisTemplate.opsForHash().get("person", "sex"));
        redisTemplate.opsForHash().entries("person").forEach((key, value) -> {
            System.out.println("key:" + key + ",value:" + value);
        });
    }
}
