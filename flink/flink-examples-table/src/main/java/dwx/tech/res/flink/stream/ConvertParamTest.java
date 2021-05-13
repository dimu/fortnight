package dwx.tech.res.flink.stream;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwx.tech.res.flink.dto.ProDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

/**
 * flink 传参测试
 *
 * @author dwx
 * @date 2021-02-24
 */
@Slf4j
public class ConvertParamTest {

	public static void main(String[] args) throws Exception {

		//step1: create execute environment
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();

		for(int index = 0; index < args.length; index++) {
			System.out.println(index + ":" + args[index]);
		}

		final ParameterTool params = ParameterTool.fromArgs(args);
		String planner = params.has("planner") ? params.get("planner") : "blink";

		String pid = params.has("pid")? params.get("pid"):"";

		//ste2: add resource
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "10.12.31.22:9092");
		properties.setProperty("group.id", "flink-test2");

//		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("kafka-test", (KafkaDeserializationSchema)new JSONKeyValueDeserializationSchema(false), properties));
		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("tm-property", new SimpleStringSchema(), properties));


		ObjectMapper objectMapper = new ObjectMapper();

		DataStream<String> deviceDataStream = dataStreamSource.map(line -> {
			log.info(line);
			ProDto device  = objectMapper.readValue(line, ProDto.class);
			return device;

//			if (pid.equalsIgnoreCase(device.getPid())) {
//				return device;
//			}
//			return null;
		}).filter(value -> value != null).map(device -> device.getPid() + ":" + device.getDevName());
		deviceDataStream.print();

//		WindowedStream<ProDto, String, TimeWindow> windowedStream= dataStreamSource.
		dataStreamSource.map(line -> {
					log.info(line);
					ProDto device  = objectMapper.readValue(line, ProDto.class);
					return device;

//			if (pid.equalsIgnoreCase(device.getPid())) {
//				return device;
//			}
//			return null;
				}).keyBy(device -> {
			return device.getDevName();
		}).window(TumblingProcessingTimeWindows.of((Time.seconds(10)))).maxBy("pid").print();



//		Properties properties1 = new Properties();
//		properties1.setProperty("bootstrap.servers", "172.19.3.194:9092");
//
//		FlinkKafkaProducer<String> sinkProducer = new FlinkKafkaProducer<String>(
//			"kafka-test", new SimpleStringSchema(), properties1
//		);

//		deviceDataStream.addSink(sinkProducer);

//		List<Integer> arrayList = new ArrayList<Integer>();
//		arrayList.add(0,1);
//		arrayList.add(1,3);
//		arrayList.forEach(item -> {
//			System.out.println(item);
//		});

//		String tableName = params.get("sinkTable");


		sEnv.execute("kafka stream convert test" + UUID.randomUUID());

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
