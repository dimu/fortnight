package dwx.tech.res.flink.stream;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * flink official keyed state example.
 *
 * custom key state, can
 *
 * @author dwx
 * @date 2020-08-14
 */
public class KeyedStateExample {

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<Event> sourceSet =  sEnv.fromElements(new Event("abc", 123), new Event("abc", 12),
				new Event("ab", 123));

		/**
		 * output: Event{key='ab', timestamp=123}, Event{key='abc', timestamp=123}
		 */
		sourceSet.keyBy(key -> key.key)
				.flatMap(new Deduplicator())
				.print();

		sEnv.execute("Keyed State Job");
	}

	private static class Event {

		public String key;

		public long timestamp;

		public Event() {

		}

		public Event(String key, long timestamp) {
			this.key = key;
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return "Event{" +
					"key='" + key + '\'' +
					", timestamp=" + timestamp +
					'}';
		}
	}

	/**
	 * custom flatmap function, will exclude the data with the same key
	 * @author dwx
	 * @date 2020-08-14
	 */
	public static class Deduplicator extends RichFlatMapFunction<Event, Event> {

		ValueState<Boolean> keyHasBeenSeen;

		StateTtlConfig ttlConfig = StateTtlConfig
				.newBuilder(Time.seconds(1))
				.setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
				.setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
				.build();

		@Override
		public void open(Configuration conf)  {
			ValueStateDescriptor<Boolean> desc = new ValueStateDescriptor<Boolean>("keyHasBeenSeen", Types.BOOLEAN);
			desc.enableTimeToLive(ttlConfig);
			keyHasBeenSeen = getRuntimeContext().getState(desc);
		}

		@Override
		public void flatMap(Event value, Collector<Event> out) throws Exception {
			if (keyHasBeenSeen.value() == null) {
				out.collect(value);
				keyHasBeenSeen.update(true);
			}
		}
	}
}
