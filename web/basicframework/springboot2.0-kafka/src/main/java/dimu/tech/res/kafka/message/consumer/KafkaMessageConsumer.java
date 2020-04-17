package dimu.tech.res.kafka.message.consumer;

import com.alibaba.fastjson.JSON;
import dimu.tech.res.kafka.mapper.UserMapper;
import dimu.tech.res.kafka.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

//    @KafkaListener(topics = "kafka-test")
    public void processMessage(String content) {
        User user = JSON.parseObject(content, User.class);
        userMapper.insert(user);
        System.out.println("consumer user " + count++);
    }

//    @KafkaListener(topics = "building")
//    public void processTestMessage(String content) {
//        System.out.println("receive message: " + content);
//    }

//    @KafkaListener(topics = "building", containerFactory = "kafkaListenerContainerFactory")
    @KafkaListener(topics = "building")
    public void manualCommitOffset(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

//        System.out.println("receive message: " + content);

//        if (null != list && !list.isEmpty()) {
//            System.out.println("consumer records size is : " + list.size() + "partition" + list.get(0).partition());
//            list.stream().forEach(record -> {
//                System.out.println("consumer thread is : " + Thread.currentThread().getName());
//                System.out.println("record header:" + record.headers().toString());
//                System.out.println("record key:" + record.key() + "record value" + record.value() + "\n");
//            });
//            ack.acknowledge();
//        }

        System.out.println("consumer thread is : " + Thread.currentThread().getName());
        System.out.println("partion is: " + consumerRecord.partition());
        System.out.println("record header:" + consumerRecord.headers().toString());
        System.out.println("record key:" + consumerRecord.key() + " record value: " + consumerRecord.value() + "\n");
        ack.acknowledge();
    }
}
