package dimu.ssm.message.consumer;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * spring boot rabbitmq consumer demo
 * @author dwx
 *
 */
@Component
public class RegisterQueueConsumer {
    
    @RabbitListener(queues = "register_queue")
    public void processMessage(String param) {
        System.out.printf("receive message %s at time %s", param, new Date());
    }
}
