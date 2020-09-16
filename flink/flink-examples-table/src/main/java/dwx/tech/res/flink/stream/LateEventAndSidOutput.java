package dwx.tech.res.flink.stream;

import org.apache.flink.streaming.api.scala.OutputTag;

/**
 * 默认使用事件事件窗口时，如果时间晚于采集点，该条数据数据是默认丢丢弃，
 * 但是flink提供了一种机制side output，可以将丢弃的数据提供采集到一个数据流中
 *
 * @author dwx
 * @date 2020-08-17
 */
public class LateEventAndSidOutput {

	public static void main(String[] args) {
//		OutputTag<Event> lateTage = new OutputTag<Event>()
	}
}
