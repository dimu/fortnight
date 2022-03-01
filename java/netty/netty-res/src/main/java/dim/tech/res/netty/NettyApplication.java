package dim.tech.res.netty;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {

	private volatile Connector connector;

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}


}
