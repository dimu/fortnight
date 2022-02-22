package dim.tech.res.disruptor.config;

import com.lmax.disruptor.EventFactory;

/**
 *
 *
 * @author dwx
 * @date 2020-05-18
 */
public class CustomEventFactory implements EventFactory<DisruptorEvent> {

    @Override
    public DisruptorEvent newInstance() {
        return new DisruptorEvent();
    }
}
