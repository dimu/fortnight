package dimu.tech.res.kafka.message.consumer;

import com.alibaba.fastjson.JSON;
import dimu.tech.res.kafka.mapper.UserMapper;
import dimu.tech.res.kafka.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
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

    @Resource
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

//    @KafkaListener(topics = "tm-setPropertiesReply")
    public void processTestMessage(String content) {
        System.out.println("receive message: " + content);
    }

//    @KafkaListener(topics = "building", containerFactory = "kafkaListenerContainerFactory")
//    @KafkaListener(topics = "kafka-test")
    public void manualCommitOffset(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

//        System.out.println("receive message: " + content);
//
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

    /**
     * 采用批处理消息，批处理消息需要进行单独配置，需要考虑延时
     * @param records 拉取的记录数
     */
    @KafkaListener(topics = "tm-event", groupId = "batch-consumer1")
    public void batchConsumerMessage(List<String> records, Acknowledgment ack) {
        System.out.println("current thread:" + Thread.currentThread().getName());
        System.out.println("pull records number: " + records.size());
//        records.stream().forEach(item -> {
//            System.out.println(" value: " + item);
//        });
        ack.acknowledge();
    }

//    @KafkaListener(topics = "kafka-test", groupId = "consumer-rate")
    public void controlConsumerRate(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        Collection<MessageListenerContainer> collection = kafkaListenerEndpointRegistry.getAllListenerContainers();
        collection.forEach(messageListenerContainer -> {
            messageListenerContainer.pause();
        });
    }
}
