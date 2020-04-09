package dimu.tech.res.kafka.config.mq.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kafka设置
 * @author dwx
 *
 */
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic testTopic() {
        return new NewTopic("kafka-test", 3, (short) 3);
    }
}
