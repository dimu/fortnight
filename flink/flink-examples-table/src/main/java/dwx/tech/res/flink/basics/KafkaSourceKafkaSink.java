//package dwx.tech.res.flink.basics;
//
//import java.util.Properties;
//
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
///**
// * 测试flink kafka as source and kafka as sink
// */
//public class KafkaSourceKafkaSink {
//
//	public static void main(String[] args) {
//		Properties kafkaConfig = new Properties();
//		kafkaConfig.setProperty("bootstrap.servers", "10.12.31.148:9092,10.12.31.149:9092,10.12.31.150:9092");
//		kafkaConfig.setProperty("group.id", "dwx-test");
////		String schemaRegistryUrl = parameterTool.getRequired("schema-registry-url");
//
//		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
////        sEnv.getConfig().disableSysoutLogging();
////        sEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//
//		String stateDir = "file:///Users/s101531/workspace/flink_datastreams/checkpoints";//parameterTool.get("stateDir");
//		String socketHost = parameterTool.get("host");
//
//
//		sEnv.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
//		sEnv.setStateBackend(new RocksDBStateBackend(stateDir, false));
//		sEnv.enableCheckpointing(60000);// start a checkpoint every 60seconds
//		CheckpointConfig config = sEnv.getCheckpointConfig();
//		config.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);// set mode to exactly-once (this is the default)
//		config.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
//
//
//		DataStreamSource<test_avro_input> sorStream1 = sEnv
//				.addSource(
//						new FlinkKafkaConsumer<>(
//								parameterTool.getRequired("input-topic"),
//								ConfluentRegistryAvroDeserializationSchema.forSpecific(test_avro_input.class, schemaRegistryUrl),
//								kafkaConfig).setStartFromEarliest());//setStartFromLatest
//
////        sorStream1.print();
//
//		DataStreamSource<test_avro_input> sorStream2 = sEnv
//				.addSource(
//						new FlinkKafkaConsumer<>(
//								"test-avro-input2",
//								ConfluentRegistryAvroDeserializationSchema.forSpecific(test_avro_input.class, schemaRegistryUrl),
//								kafkaConfig).setStartFromEarliest()); //setStartFromEarliest
//
//
//		StreamTableEnvironment tableEnv = StreamTableEnvironment.create(sEnv);
//
//		Table t1 = tableEnv.fromDataStream(sorStream1);
//		Table t2 = tableEnv.fromDataStream(sorStream2);
//
//
//
//		Table result1 = t1.select("name.lowerCase()") ;
//		Table result2 = t2.select("name  as t2name, favoriteNumber as t2favoriteNumber ") ;
//
//
//
//		Table result = t1.join(result2).where("name = t2name").select("name, t2name,  favoriteNumber ");
//		tableEnv.toAppendStream(result, Row.class).print();
//
//
//		System.out.println(sEnv.getExecutionPlan());
//
//
//		sEnv.execute("Kafka 0.10 Confluent Schema Registry AVRO Example");
//	}
//}
