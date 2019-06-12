package dwx.tech.res.msg.rabbitmq.producer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

	@Bean
	Queue queue() {
		return new Queue("test");
	}
}
