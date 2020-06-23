package dimu.tech.res.spark.message.consumer;

import dimu.tech.res.spark.common.AbstractKafkaHandler;
import dimu.tech.res.spark.common.CaptureException;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ThingTopicHandler extends AbstractKafkaHandler<String> {

    public ThingTopicHandler(boolean stopOrPause, String containerId, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        super(stopOrPause, containerId, kafkaListenerEndpointRegistry);
    }

    @Override
    public boolean process(String s) throws CaptureException, NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG").nextBoolean();
    }
}
