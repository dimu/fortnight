package dwx.tech.res.msg.rabbitmq.producer.sender;

import java.util.Random;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwx.tech.res.msg.rabbitmq.producer.model.User;

@Component
@EnableScheduling
public class UserSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Scheduled(initialDelay = 3000, fixedRate = 3000)
	public void sender() throws AmqpException, JsonProcessingException {
		User user = new User();
		user.setAddress("互联网产业园");
		user.setAge(new Random().nextInt(32));
		user.setName("sun");
		System.out.println(user.toString());
		this.rabbitTemplate.convertAndSend("test", new ObjectMapper().writeValueAsString(user));
	}
}
