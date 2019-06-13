package dimu.ssm.message.provider;

import javax.annotation.Resource;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka message producer
 * @author dwx
 *
 */
@Component
public class KafkaMessageProducer {

    @Resource
    private KafkaTemplate<String, Object> kafkaTempalte;
    
    public void produceMessage() {
        kafkaTempalte.send("kafka-test", "kafka message");
    }
}
