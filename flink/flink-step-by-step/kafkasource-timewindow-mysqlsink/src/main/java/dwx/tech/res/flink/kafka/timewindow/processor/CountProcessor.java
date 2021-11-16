package dwx.tech.res.flink.kafka.timewindow.processor;

import java.util.Date;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author dwx
 */
public class CountProcessor extends ProcessWindowFunction<
		String,                  // input type
		Tuple3<String, Date, Integer>,  // output type
		String,                         // key type
		TimeWindow> {                   // window type

	@Override
	public void process(
			String key,
			Context context,
			Iterable<String> events,
			Collector<Tuple3<String, Date, Integer>> out) {
		System.out.println("step into process");
		int count = 0;
		for (String event : events) {

			count++;
		}
		out.collect(Tuple3.of(key, new Date(context.window().getEnd()), count));
	}
}
