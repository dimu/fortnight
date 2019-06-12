package dimu.ssm.message.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka message consumer demo
 * @author dwx
 *
 */
@Component
public class KafkaMessageConsumer {

    @KafkaListener(topics = "kafka-test")
    public void processMessage(String content) {
        System.out.println("kafka consumer message is :" + content);
    }
}
