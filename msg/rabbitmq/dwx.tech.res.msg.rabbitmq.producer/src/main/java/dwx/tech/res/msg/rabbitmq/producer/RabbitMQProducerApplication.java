package dwx.tech.res.msg.rabbitmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dwx.tech.res.msg.rabbitmq.producer.sender.UserSender;

@SpringBootApplication
public class RabbitMQProducerApplication implements CommandLineRunner{
	
//	@Autowired
//	private UserSender userSender;

	public static void main(String[] args) {
		SpringApplication.run(RabbitMQProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		userSender.sender();
//		System.out.println("sender over");
	}
}
