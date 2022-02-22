package dim.tech.res.disruptor.config;

import lombok.Data;
import lombok.ToString;

/**
 * 数据与事件解析后的键值对
 *
 * @author dwx
 * @date 2020-05-18
 */
@Data
@ToString
public class DisruptorEvent {

    private String key;

    private String value;
}
