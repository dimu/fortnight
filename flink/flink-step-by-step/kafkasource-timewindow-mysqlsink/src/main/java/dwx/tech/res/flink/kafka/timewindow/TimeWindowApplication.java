package dwx.tech.res.flink.kafka.timewindow;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import dwx.tech.res.flink.kafka.timewindow.dto.KafkaDto;
import dwx.tech.res.flink.kafka.timewindow.processor.CountProcessor;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author dwx
 */
@SpringBootApplication
public class TimeWindowApplication implements CommandLineRunner {

//	@Resource
//	private JdbcT

	private static final int TIME_INTERVAL = 1;

	public static void main(String[] args) {
		SpringApplication.run(TimeWindowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
		/**
		 * 必须配置上数据流时间特性
		 */
		sEnv.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "10.12.31.148:9092,10.12.31.149:9092,10.12.31.150:9092");
		properties.setProperty("group.id", "flink-test");
		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("thing_client_upstream", new SimpleStringSchema(), properties));
		ObjectMapper objectMapper = new ObjectMapper();
		dataStreamSource.keyBy(new KeySelector<String, String>() {
			@Override
			public String getKey(String value) throws Exception {
//				System.out.println(value);
				KafkaDto dto  = objectMapper.readValue(value, KafkaDto.class);
				return dto.getDevKey() + ":" + dto.getProductId();
			}
		}).window(TumblingEventTimeWindows.of(Time.minutes(TIME_INTERVAL)))
				.process(new CountProcessor()).addSink(new SinkFunction<Tuple3<String, Date, Integer>>(){
			@Override
			public void invoke(Tuple3<String, Date, Integer> value, Context context) throws Exception {

			}
		});



		sEnv.execute("Data count!");
	}
}
