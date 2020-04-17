//package dimu.tech.res.kafka.config.mq.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * kafka设置
// * @author dwx
// *
// */
//@Configuration
//public class KafkaConfig {
//
////    @Bean
////    public NewTopic testTopic() {
////        return new NewTopic("kafka-test", 3, (short) 3);
////    }
//
//    @Resource
//    private KafkaProperties kafkaProperties;
//
//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfigs()));
//        factory.setConcurrency(6);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
//
//        return factory;
//    }
//
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>(16);
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "dwx-consumer");
//        //key跟value的序列化方式必须指定
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer());
//
//        return props;
//    }
//}
