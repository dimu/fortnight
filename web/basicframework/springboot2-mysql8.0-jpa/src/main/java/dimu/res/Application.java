package dimu.res;

import dimu.res.message.provider.KafkaMessageProducer;
import dimu.res.message.provider.RegisterMessageProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * springboot2.0 mybatis integration
 * @author dwx
 *
 */
@SpringBootApplication
public class Application implements ApplicationRunner{

    @Resource 
    private RedisTemplate<String, String> stringRedisTemplate;

    //    @Resource
//    private StringRedisTemplate stringRedisTemplate;
    
    @Resource 
    private RegisterMessageProvider messageProvider;
    
    @Resource 
    private KafkaMessageProducer kafkaMessageProducer;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
