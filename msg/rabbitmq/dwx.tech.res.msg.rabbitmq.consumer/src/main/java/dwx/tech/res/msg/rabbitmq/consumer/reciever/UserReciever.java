package dwx.tech.res.msg.rabbitmq.consumer.reciever;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwx.tech.res.msg.rabbitmq.consumer.model.DeserialUser;

@Component
public class UserReciever {

	@RabbitListener(queues="test")
	public void reciever(String user) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("reciver:" + user);
		System.out.println(new ObjectMapper().readValue(user, DeserialUser.class).getAge());
	}
}
