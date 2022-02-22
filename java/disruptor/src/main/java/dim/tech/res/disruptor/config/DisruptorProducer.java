package dim.tech.res.disruptor.config;

import com.lmax.disruptor.RingBuffer;

/**
 * 解析kafka数据直接写入ringbuffer中
 *
 * @author dwx
 * @date 2020-05-18
 */
public class DisruptorProducer {

    private final RingBuffer<DisruptorEvent> ringBuffer;

    public DisruptorProducer(RingBuffer<DisruptorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发布消息
     *
     * @param key 关键值
     * @param val 对应的值
     */
    public void publish(String key, String val) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            DisruptorEvent event = ringBuffer.get(sequence);
            event.setKey(key);
            event.setValue(val);
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }

}
