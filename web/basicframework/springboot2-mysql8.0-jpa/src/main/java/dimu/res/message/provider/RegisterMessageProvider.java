package dimu.res.message.provider;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 * @author dwx
 *
 */
@Component
public class RegisterMessageProvider {

    @Resource
    private AmqpTemplate amqpTemplate;
    
    public void produceMessage() {
        amqpTemplate.convertAndSend("register_queue", RandomStringUtils.randomAlphanumeric(2, 10));
    }
}
