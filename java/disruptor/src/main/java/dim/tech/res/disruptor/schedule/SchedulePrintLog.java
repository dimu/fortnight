package dim.tech.res.disruptor.schedule;

import javax.annotation.Resource;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
public class SchedulePrintLog {

	@Resource
	private Disruptor disruptor;

//	@Scheduled(cron="30 * * * * ?")
	public void schedulePrintDisruptorSize() {
		long cursor = disruptor.getCursor();
		long next = disruptor.getRingBuffer().next();
		log.info("cursor: {}, next: {}, lag: {}", cursor, next, next-cursor-1);
	}
}
