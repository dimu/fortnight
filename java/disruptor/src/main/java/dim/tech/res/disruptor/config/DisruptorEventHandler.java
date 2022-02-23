package dim.tech.res.disruptor.config;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * kafka 消息处理器
 *
 * @author dwx
 * @date 2020-05-12
 */
@Component
@Slf4j
public class DisruptorEventHandler implements WorkHandler<DisruptorEvent> {

    public static AtomicInteger count = new AtomicInteger(0);

    private final ReentrantLock lock = new ReentrantLock();

    /**
     *
     * @param msg
     */
    @Override
    public void onEvent(DisruptorEvent msg) {
        lock.lock();
        try {
            log.info("Thread {} hod count {}", Thread.currentThread().getName(), lock.getHoldCount());
            log.info("lock waiting queue length: {}", lock.getQueueLength());
            proessEvent();
            log.info("consumer msg:" + msg.toString());
            log.info("thread {} consumer count {}", Thread.currentThread().getName(), count.incrementAndGet());
        } catch (Exception e) {
            log.info("【fail_msg】:{}", msg.toString());
            log.error("【KafkaMsgHandler】", e);
        } finally {
            lock.unlock();
            log.info("thread {} process over!", Thread.currentThread().getName());
        }
    }

    /**
     * mock event process method
     * @throws InterruptedException
     */
    public void proessEvent() throws InterruptedException {
        //The thread does not lose ownership of any monitors.
        TimeUnit.SECONDS.sleep(5);
        //todo: balabala
    }
}
