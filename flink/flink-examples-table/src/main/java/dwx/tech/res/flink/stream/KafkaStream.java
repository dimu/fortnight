package dwx.tech.res.flink.stream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.formats.json.JsonRowSerializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.types.Row;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 消费kafka，进行简单测试
 *
 * @author dwx
 * @date 2020-08-11
 */
public class KafkaStream {

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
			Device device  = objectMapper.readValue(line, Device.class);

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

	public static class User implements Serializable {

		private static final long serialVersionUID = -4787773422434404792L;

		private Long id;

		private String account;

		private String password;

		private String salt;

		private String mobile;

		private String mail;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getSalt() {
			return salt;
		}

		public void setSalt(String salt) {
			this.salt = salt;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

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
