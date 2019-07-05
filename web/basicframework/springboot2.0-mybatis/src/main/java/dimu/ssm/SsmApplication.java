package dimu.ssm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.redis.core.RedisTemplate;

import dimu.ssm.config.redis.distributedlock.DistributedLockCallback;
import dimu.ssm.config.redis.distributedlock.RedisLockTemplate;
import dimu.ssm.message.provider.KafkaMessageProducer;
import dimu.ssm.message.provider.RegisterMessageProvider;
import redis.clients.jedis.JedisPool;

/**
 * springboot2.0 mybatis integration
 * @author dwx
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="dimu.ssm")
public class SsmApplication implements ApplicationRunner{

    @Resource 
    private RedisTemplate<String, String> stringRedisTemplate;
    
    @Resource
    private JedisPool jedisPool;
    
//    @Resource 
//    private StringRedisTemplate stringRedisTemplate;
    
    @Resource 
    private RegisterMessageProvider messageProvider;
    
    @Resource 
    private KafkaMessageProducer kafkaMessageProducer;
    
    public static void main(String[] args) {
        SpringApplication.run(SsmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        stringRedisTemplate.opsForValue().set("name", "dimu", 60, TimeUnit.SECONDS);
        
        messageProvider.produceMessage();
        
        kafkaMessageProducer.produceMessage();
        
//        RedisLockTemplate<String> redisLockTemplate = new RedisLockTemplate<>(jedisPool);
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        List<Future<String>> results = new ArrayList<Future<String>>(10000);
//
//        for (int i = 0; i < 10000; i++) {
//            results.add(executor.submit(new Callable<String>() {
//
//                @Override
//                public String call() throws Exception {
//                    redisLockTemplate.execute("redis-lock", 3000, new DistributedLockCallback<String>() {
//
//                        @Override
//                        public String onLockingSucceeded() throws InterruptedException {
//                            System.out.println("lock sucess" + Thread.currentThread().getName());
//                            return null;
//                        }
//
//                        @Override
//                        public String onLockingFailed() throws InterruptedException {
//                            System.out.println("lock failure" + Thread.currentThread().getName());
//                            return null;
//                        }
//                        
//                    });
//                    return null;
//                }
//            }));
//        }
//        executor.shutdown();
    }
}
