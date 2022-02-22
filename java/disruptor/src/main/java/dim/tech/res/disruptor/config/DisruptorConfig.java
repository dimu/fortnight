package dim.tech.res.disruptor.config;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * disruptor配置类相关
 *
 * @author dwx
 * @date 2020-05-18
 */
@Configuration
public class DisruptorConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${disruptor.buffer-size:8192}")
    private int bufferSize;

    @Value("${disruptor.worker-num:8}")
    private int workerNum;

    private EventHandlerGroup<DisruptorEvent> eventHandlerGroup;

    @Bean
    public Disruptor disruptor() {
        CustomEventFactory eventFactory = new CustomEventFactory();
        DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory();
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        Disruptor<DisruptorEvent> disruptor = new Disruptor<DisruptorEvent>(eventFactory, bufferSize, defaultThreadFactory, ProducerType.MULTI, waitStrategy);
        WorkHandler[] workHandlers = new WorkHandler[workerNum];
        for (int i = 0; i < workerNum; i++) {
            WorkHandler workHandler = this.applicationContext.getBean(DisruptorEventHandler.class);
            workHandlers[i] = workHandler;
        }
        eventHandlerGroup = disruptor.handleEventsWithWorkerPool(workHandlers);
        disruptor.start();

        return disruptor;
    }

    @Bean
    public DisruptorProducer producer(Disruptor disruptor) {
        RingBuffer<DisruptorEvent> ringBuffer = disruptor.getRingBuffer();
        return new DisruptorProducer(ringBuffer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public EventHandlerGroup<DisruptorEvent> getEventHandlerGroup() {
        return eventHandlerGroup;
    }
}
