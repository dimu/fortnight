package dwx.tech.res.flink.stream.time;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * flink explicitly supports three different notions of time:
 * event time: the time when an event occurred, as recorded by the device producing the event
 * ingestion time: a timestamp recorded by Flink at the moment it ingests the event
 * processing time: the time when a specific operator in your pipeline is processing the event
 *
 * watermarks vs completeness
 *
 * @author dwx
 * @date 2020-08-14
 */
public class EventTimeExample {

	public static void main(String[] args) {
		final StreamExecutionEnvironment env =
				StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

//		WatermarkStrategy<Event> strategy = WatermarkStrategy
//				.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(20))
//				.withTimestampAssigner((event, timestamp) -> event.timestamp);
	}
}
