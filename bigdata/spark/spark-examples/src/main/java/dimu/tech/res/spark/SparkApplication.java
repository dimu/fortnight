package dimu.tech.res.spark;

import dimu.tech.res.spark.message.provider.KafkaMessageProducer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * spark examples
 * @author dwx
 *
 */
@SpringBootApplication
public class SparkApplication implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    @Resource
    private KafkaMessageProducer kafkaMessageProducer;

    @Resource
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public static void main(String[] args) {
        SpringApplication.run(SparkApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        kafkaMessageProducer.produceMessage();
        kafkaMessageProducer.produceEventMessage();
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

    /**
     * mannual close kafka consumer
     * @param event 应用关闭事件
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("application begin to close!");
        Collection<MessageListenerContainer> list  = kafkaListenerEndpointRegistry.getAllListenerContainers();
        System.out.println("container size: " + list.size());
        list.forEach(item -> {
            item.getAssignedPartitions().forEach(partition ->
            {
                System.out.println("topic:" + partition.topic() + " partition:" + partition.partition());
            });
            item.stop(() -> {
                System.out.println(item.getContainerProperties().toString() + " stop finish");
            });
        });
    }
}
