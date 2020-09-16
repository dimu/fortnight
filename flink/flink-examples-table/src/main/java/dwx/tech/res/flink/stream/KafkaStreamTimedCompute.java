package dwx.tech.res.flink.stream;

import java.io.Serializable;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

/**
 * test flink stream timely compute
 */
public class KafkaStreamTimedCompute {
	public static void main(String[] args) throws Exception {

		//step1: create execute environment
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
		/**
		 * 必须配置上数据流时间特性
		 */
		sEnv.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);

		//ste2: add resource
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "10.12.31.148:9092");
		properties.setProperty("group.id", "flink-test");

//		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("kafka-test", (KafkaDeserializationSchema)new JSONKeyValueDeserializationSchema(false), properties));
		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("thing_client_upstream", new SimpleStringSchema(), properties));


		ObjectMapper objectMapper = new ObjectMapper();
//
//		DataStream<String> deviceDataStream = dataStreamSource.map(line -> {
//			System.out.println(line);
//			KafkaStream.Device device  = objectMapper.readValue(line, KafkaStream.Device.class);
//
//			if ("notify".equalsIgnoreCase(device.getMethod())) {
//				return device;
//			}
//			return null;
//		}).filter(value -> value != null).map(device -> device.getDevKey() + ":" + device.getProductId());

		dataStreamSource.keyBy(new KeySelector<String, String>() {
			@Override
			public String getKey(String value) throws Exception {
//				System.out.println(value);
				KafkaStream.Device device  = objectMapper.readValue(value, KafkaStream.Device.class);
				return device.getDevKey() + ":" + device.getProductId();
			}
		}).window(TumblingEventTimeWindows.of(Time.minutes(1)))
				.process(new MyWastefulMax()).print();


//		WindowedStream<Device, String, TimeWindow> windowedStream= deviceDataStream.keyBy(device -> device.getDevKey())
//				.timeWindow(Time.minutes(10))
//				.aggregate();

//		Properties properties1 = new Properties();
//		properties1.setProperty("bootstrap.servers", "172.19.3.194:9092");
//
//		FlinkKafkaProducer<String> sinkProducer = new FlinkKafkaProducer<String>(
//				"kafka-test", new SimpleStringSchema(), properties1
//		);
//
//		deviceDataStream.addSink(sinkProducer);

//		List<Integer> arrayList = new ArrayList<Integer>();
//		arrayList.add(0,1);
//		arrayList.add(1,3);
//		arrayList.forEach(item -> {
//			System.out.println(item);
//		});

		sEnv.execute("kafka stream test");

		System.out.println("over!");
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Device {

		private String devKey;

		private String productId;

		public String method;

		String getDevKey() {
			return devKey;
		}

		void setDevKey(String devKey) {
			this.devKey = devKey;
		}

		String getProductId() {
			return productId;
		}

		void setProductId(String productId) {
			this.productId = productId;
		}

		String getMethod() {
			return method;
		}

		void setMethod(String method) {
			this.method = method;
		}
	}

	public static class MyWastefulMax extends ProcessWindowFunction<
			String,                  // input type
			Tuple3<String, Long, Integer>,  // output type
			String,                         // key type
			TimeWindow> {                   // window type

		@Override
		public void process(
				String key,
				Context context,
				Iterable<String> events,
				Collector<Tuple3<String, Long, Integer>> out) {
			System.out.println("step into process");
			int count = 0;
			for (String event : events) {

				count++;
			}
			out.collect(Tuple3.of(key, context.window().getEnd(), count));
		}
	}
}
