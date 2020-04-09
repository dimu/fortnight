package dimu.tech.res.kafka.message.provider;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import dimu.tech.res.kafka.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * kafka message producer
 * @author dwx
 *
 */
@Component
public class KafkaMessageProducer {

    private static volatile int count = 0;

    @Resource
    private KafkaTemplate<String, Object> kafkaTempalte;

    @Resource
    private Executor poolTaskExecutor;

    public void produceMessage() {
        for(;;) {
            poolTaskExecutor.execute(()->{
                kafkaTempalte.send("kafka-test", randomUser());
                System.out.println(Thread.currentThread().getName() + " producer count " + count++);
            });
        }
    }

    public String randomUser() {
        User user = new User();
        user.setAccount(UUID.randomUUID().toString().replace("-", ""));
        user.setPassword(new SecureRandom().generateSeed(8).toString());

        return JSON.toJSONString(user);
    }

}
