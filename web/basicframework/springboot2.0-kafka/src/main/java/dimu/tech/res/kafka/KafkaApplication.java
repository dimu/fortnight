package dimu.tech.res.kafka;

import dimu.tech.res.kafka.message.provider.KafkaMessageProducer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * springboot2.0 mybatis integration
 * @author dwx
 *
 */
@SpringBootApplication
public class KafkaApplication implements ApplicationRunner{

    @Resource
    private KafkaMessageProducer kafkaMessageProducer;
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        kafkaMessageProducer.produceMessage();
//        kafkaMessageProducer.produceEventMessage();
//        kafkaMessageProducer.produceCmdMessage();
        
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
