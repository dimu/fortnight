package dim.tech.res.disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class DisruptorApplication implements TomcatConnectorCustomizer,
		ApplicationListener<ContextClosedEvent> {

	private volatile Connector connector;

	public static void main(String[] args) {
		SpringApplication.run(DisruptorApplication.class, args);

		/**
		 * Another method to implement shut down hook, we can use it
		 * to release resources gracefully when close your application, such as waiting
		 * memory queue date to be consumed out.
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("application is shutting down! Current thread is " + Thread.currentThread().getName());
		}));
	}

	@Override
	public void customize(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if (this.connector == null) {
			return;
		}
		this.connector.pause();
		Executor executor = this.connector.getProtocolHandler().getExecutor();
		if (executor instanceof ThreadPoolExecutor) {
			try {
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
				threadPoolExecutor.shutdown();
				if (!threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
					System.out.println("Tomcat thread pool did not shut down gracefully within "
							+ "30 seconds. Proceeding with forceful shutdown");
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out.println("shut down over!");
		}
	}
}
