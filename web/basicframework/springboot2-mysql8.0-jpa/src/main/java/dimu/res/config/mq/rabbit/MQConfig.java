package dimu.ssm.config.mq.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot rabbitmq config
 * 声明创建队列 
 * @author dwx
 *
 */
@Configuration
public class MQConfig {

    //queue name definition
    public static final String  REGISTER_QUEUE = "register_queue";
    
    /**
     * 声明
     * @return
     */
    @Bean
    public Queue registerQueue() {
        return new Queue(REGISTER_QUEUE, true);
    }
}
