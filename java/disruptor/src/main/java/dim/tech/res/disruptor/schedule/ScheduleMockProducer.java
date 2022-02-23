package dim.tech.res.disruptor.schedule;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import dim.tech.res.disruptor.config.DisruptorConfig;
import dim.tech.res.disruptor.config.DisruptorProducer;
import dim.tech.res.disruptor.config.DisruptorEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
public class ScheduleMockProducer {

	@Resource
	private Disruptor disruptor;

	@Resource
	private DisruptorConfig disruptorConfig;

	@Resource
	List<WorkHandler<DisruptorEvent>> workHandlerList;

	@Resource
	private DisruptorProducer disruptorProducer;

	private AtomicInteger count = new AtomicInteger(0);

	@Scheduled(cron="*/1 * * * * ?")
	public void schedulePrintDisruptorSize() {
		String key = UUID.randomUUID().toString();
		String value = UUID.randomUUID().toString();
		disruptorProducer.publish(key,value);

//		System.out.println("producer count: " + count.incrementAndGet());

		long cursor = disruptor.getCursor();


//		log.info("cursor: {},  min-current:{}", cursor, disruptor.getRingBuffer().getMinimumGatingSequence());
//		log.info("cursor lag: {}", cursor - disruptorConfig.getEventHandlerGroup().asSequenceBarrier().getCursor());
		log.info("disruptor remaining size: {}",disruptor.getRingBuffer().remainingCapacity());
	}
}
