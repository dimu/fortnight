package dimu.tech.res.kafka.message.consumer;

import com.alibaba.fastjson.JSON;
import dimu.tech.res.kafka.mapper.UserMapper;
import dimu.tech.res.kafka.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * kafka message consumer demo
 * @author dwx
 *
 */
@Component
public class KafkaMessageConsumer {

    private static volatile int count = 0;

    @Resource
    private UserMapper userMapper;

    @KafkaListener(topics = "kafka-test")
    public void processMessage(String content) {
        User user = JSON.parseObject(content, User.class);
        userMapper.insert(user);
        System.out.println("consumer user " + count++);
    }
}
