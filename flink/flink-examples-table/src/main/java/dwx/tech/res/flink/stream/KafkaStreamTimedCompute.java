package dwx.tech.res.flink.stream;

import java.io.Serializable;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

/**
 * test flink stream timely compute
 */
public class KafkaStreamTimedCompute {
	public static void main(String[] args) throws Exception {

		//step1: create execute environment
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();

		//ste2: add resource
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "10.12.31.148:9092");
		properties.setProperty("group.id", "flink-test");

//		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("kafka-test", (KafkaDeserializationSchema)new JSONKeyValueDeserializationSchema(false), properties));
		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("thing_client_upstream", new SimpleStringSchema(), properties));


		ObjectMapper objectMapper = new ObjectMapper();

		DataStream<String> deviceDataStream = dataStreamSource.map(line -> {
			System.out.println(line);
			KafkaStream.Device device  = objectMapper.readValue(line, KafkaStream.Device.class);

			if ("notify".equalsIgnoreCase(device.getMethod())) {
				return device;
			}
			return null;
		}).filter(value -> value != null).map(device -> device.getDevKey() + ":" + device.getProductId());

//		WindowedStream<Device, String, TimeWindow> windowedStream= deviceDataStream.keyBy(device -> device.getDevKey())
//				.timeWindow(Time.minutes(10))
//				.aggregate();

		Properties properties1 = new Properties();
		properties1.setProperty("bootstrap.servers", "172.19.3.194:9092");

		FlinkKafkaProducer<String> sinkProducer = new FlinkKafkaProducer<String>(
				"kafka-test", new SimpleStringSchema(), properties1
		);

		deviceDataStream.addSink(sinkProducer);

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
}
