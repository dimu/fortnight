package dim.tech.res.disruptor.config;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * kafka 消息处理器
 *
 * @author dwx
 * @date 2020-05-12
 */
@Component
@Slf4j
//@Scope("prototype")
public class KafkaMsgHandler implements WorkHandler<KafkaMsg> {

    public static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onEvent(KafkaMsg msg) {
        try {
//            this.coreService.dealMsg(msg);
            TimeUnit.SECONDS.sleep(5);
            System.out.println("consumer msg:" + msg.toString());
            log.info("consumer count {}", count.incrementAndGet());
        } catch (Exception e) {
            log.info("【fail_msg】:{}", msg.toString());
            log.error("【KafkaMsgHandler】", e);
        }
    }
}
