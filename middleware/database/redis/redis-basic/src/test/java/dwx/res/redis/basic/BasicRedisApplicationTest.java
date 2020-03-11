package dwx.res.redis.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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

    /**
     * 基本的字符串操作
     */
    @Test
    public void setString() {
        redisTemplate.opsForValue().set("name", "dwx");
        System.out.println(redisTemplate.opsForValue().get("name"));
//        redisTemplate.opsForValue().set("age", 32);
//        redisTemplate.opsForValue().decrement("age");
//        System.out.println(redisTemplate.opsForValue().get("age"));
    }

    /**
     * redisTemplate类默认采取JdkSerializationRedisSerializer序列化与反序列工具，
     * 在序列化时会加入类相关信息，在调用decrement方法时会报错
     */
    @Test
    public void decrementInteger() {
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.opsForValue().set("age", 32);
        redisTemplate.opsForValue().decrement("age");
        System.out.println(redisTemplate.opsForValue().get("age"));
    }

    /**
     * 基本的hash操作
     */
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

    /**
     * List basic use method, list behavior like a queue, cant push pop from left to right
     * the result: cat tiger dog pig birds
     */
    @Test
    public void setList() {
        redisTemplate.opsForList().leftPushAll("animals", "tiger", "cat");
        redisTemplate.opsForList().rightPushAll("animals", "dog", "pig", "birds");
        redisTemplate.opsForList().range("animals", 0, -1).forEach(item->{
            System.out.println(item);
        });
    }

    /**
     * collection set basic use.
     * key methods: union, difference,intersect
     */
    @Test
    public void setSet() {
        redisTemplate.opsForSet().add("class1", "lucy", "lily", "tom");
        redisTemplate.opsForSet().add("class2", "tim", "lucy", "bob");

        /**
         * the difference output: lily & tom
         */
        redisTemplate.opsForSet().difference("class1", "class2").forEach(item -> System.out.printf("%s  ", item));

        /**
         * the intersect output: lucy
         */
        System.out.println();
        redisTemplate.opsForSet().intersect("class1", "class2").forEach(item -> System.out.printf("%s  ", item));

        /**
         * the union output: lucy,lily,tom,tim,bob
         */
        System.out.println();
        redisTemplate.opsForSet().union("class1", "class2").forEach(item -> System.out.printf("%s  ", item));

    }

    /**
     * basic use for zset
     * the members with same score are ordered by lexicographically.
     * the following output: pear aa appple banana lemon strawberry.
     */
    @Test
    public void setZset() {
        redisTemplate.opsForZSet().add("product","banana", 2 );
        redisTemplate.opsForZSet().add("product","pear", 1 );
        redisTemplate.opsForZSet().add("product","apple", 2 );
        redisTemplate.opsForZSet().add("product","lemon", 3 );
        redisTemplate.opsForZSet().add("product","strawberry", 4);
        redisTemplate.opsForZSet().add("product","aa", 2 );

        redisTemplate.opsForZSet().range("product", 0 , -1).forEach(item -> System.out.println(item));
    }

}
