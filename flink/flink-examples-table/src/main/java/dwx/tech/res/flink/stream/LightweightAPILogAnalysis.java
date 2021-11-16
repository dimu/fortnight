package dwx.tech.res.flink.stream;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwx.tech.res.flink.dto.ProDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import static org.apache.flink.table.api.Expressions.$;

/**
 * flink 传参测试
 *
 * @author dwx
 * @date 2021-02-24
 */
@Slf4j
public class LightweightAPILogAnalysis {

	public static void main(String[] args) throws Exception {

		//step1: create execute environment
		StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
		final ParameterTool params = ParameterTool.fromArgs(args);
		String planner = params.has("planner") ? params.get("planner") : "blink";


		//ste2: add resource
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "10.12.31.58:9092");
		properties.setProperty("group.id", "flink-test2");

		DataStream<String> dataStreamSource = sEnv.addSource(new FlinkKafkaConsumer<String>("elk-proxy-api",
				new SimpleStringSchema(), properties));


		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		DataStream<ApiLog> apiLogDataStream = dataStreamSource.map(line -> {
			System.out.println(line);
			JsonNode outNode = objectMapper.readTree(line);
			String message = outNode.get("message").asText();
			JsonNode messageNode = objectMapper.readTree(message);
			ApiLog apiLog  = new  ApiLog();
			apiLog.setLevel(messageNode.get("level").asText());
			apiLog.setCreateTime(messageNode.get("log_time").asText());
			apiLog.setStatus(messageNode.get("status").asText());
			apiLog.setTraceId(messageNode.get("trace_id").asText());
			apiLog.setType(messageNode.get("type").asText());
			JsonNode contentNode = messageNode.get("content");
			if (null != contentNode) {

				if (null != contentNode.get("host")) {
					apiLog.setHost(contentNode.get("host").asText());
				}

				if (null != contentNode.get("path")) {
					apiLog.setPath(contentNode.get("path").asText());
				}

				if (null != contentNode.get("query")) {
					apiLog.setQuery(contentNode.get("query").asText());
				}

				if (null != contentNode.get("body")) {
					apiLog.setBody(contentNode.get("body").asText());
				}

				if (null != contentNode.get("user_id")) {
					apiLog.setUserId(Long.valueOf(contentNode.get("user_id").asText()));
				}

				if (null != contentNode.get("role_id")) {
					apiLog.setRoleId(contentNode.get("role_id").asText());
				}

				if (null != contentNode.get("namespace")) {
					apiLog.setNamespace(contentNode.get("namespace").asText());
				}

				if (null != contentNode.get("action")) {
					apiLog.setAction(contentNode.get("action").asText());
				}

				if (null != contentNode.get("code")) {
					apiLog.setCode(contentNode.get("code").asText());
				}

				if (null != contentNode.get("method")) {
					apiLog.setMethod(contentNode.get("method").asText());
				}

			}

			apiLog.setDayId(sdf.format(Calendar.getInstance().getTime()));
			return apiLog;


		});
//		deviceDataStream.print();

		String sinkTableSql = "CREATE TABLE api_log_ext_studio ("
				+ "  host string,"
				+ "  path string,"
				+ "  query string,"
				+ "  body string,"
				+ "  user_id bigint,"
				+ "  role_id string,"
				+ "  namespace string,"
				+ "  action string,"
				+ "  code string,"
				+ "  `method` string,"
				+ "  level string,"
				+ "  create_time string,"
				+ "  status string,"
				+ "  trace_id string,"
				+ "  type string,"
				+ "  day_id string"
				+ ") WITH ("
				+ "   'connector' = 'jdbc',"
				+ "   'url' = 'jdbc:mysql://172.19.3.47:3306/mongo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT%2B8&useSSL=false',"
				+ "   'table-name' = 'api_logs_ext_studio',"
				+ "   'username' = 'mongo',"
				+ "   'password' = 'mongo_test'"
				+ ")";


		StreamTableEnvironment tEnv = StreamTableEnvironment.create(sEnv);
		tEnv.executeSql(sinkTableSql);

		Table apiTable = tEnv.fromDataStream(apiLogDataStream, $("host"), $("path"), $("query"),
				$("body"), $("userId").as("user_id"), $("roleId").as("role_id"),
				$("namespace"),$("action"), $("code"), $("method"),$("level"),
				$("createTime").as("create_time"), $("status"),$("traceId").as("trace_id"),
				$("type"), $("dayId").as("day_id"));


		apiTable.executeInsert("api_log_ext_studio");


////		WindowedStream<ProDto, String, TimeWindow> windowedStream= dataStreamSource.
//		dataStreamSource.map(line -> {
//					log.info(line);
//			JsonNode outNode = objectMapper.readTree(line);
//			String message = outNode.get("message").asText();
//			JsonNode messageNode = objectMapper.readTree(message);
//
//
//					return device;
//
////			if (pid.equalsIgnoreCase(device.getPid())) {
////				return device;
////			}
////			return null;
//				}).keyBy(device -> {
//			return device.getDevName();
//		}).window(TumblingProcessingTimeWindows.of((Time.seconds(10)))).maxBy("pid").print();



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


//		sEnv.execute("kafka stream api call test!");

		System.out.println("over!");
	}

	/**
	 * stream 与table进行转换时，pojo有如下要求：
	 * https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/types_serialization.html#pojos
	 * 否则无法解析，因为stream认为pojo为一个atomic type
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ApiLog implements Serializable {

		public static final long serialVersionUID = -4787773422434404792L;

		@JsonProperty(index = 1)
		public String host;

		@JsonProperty(index = 2)
		public String path;

		@JsonProperty(index = 3)
		public String query;

		@JsonProperty(index = 4)
		public String body;

		@JsonProperty(index = 5)
		public Long userId;

		@JsonProperty(value = "role_id", index = 6)
		public String roleId;

		@JsonProperty( index = 7)
		public String namespace;

		@JsonProperty(index = 8)
		public String action;

		@JsonProperty( index = 9)
		public String code;

		@JsonProperty(index = 10)
		public String method;

		@JsonProperty(index = 11)
		public String level;

		@JsonProperty(value = "log_time", index = 12)
		public String createTime;

		@JsonProperty(index = 13)
		public String status;

		@JsonProperty(value = "trace_id", index = 14)
		public String traceId;

		@JsonProperty(index = 15)
		public String type;

		@JsonProperty(index = 16)
		public String dayId;

		/**
		 * 必须有无参构造函数
		 * org.apache.flink.table.api.ValidationException:
		 * Too many fields referenced from an atomic type
		 */
		public ApiLog() {

		}

		ApiLog(String host, String path, String query, String body, Long userId, String roleId, String namespace, String action, String code,
				String method, String level, String createTime, String status, String traceId, String type, String dayId) {
			this.host = host;
			this.path = path;
			this.query = query;
			this.body = body;
			this.userId = userId;
			this.roleId = roleId;
			this.namespace = namespace;
			this.action = action;
			this.code = code;
			this.method = method;
			this.level = level;
			this.createTime = createTime;
			this.status = status;
			this.traceId = traceId;
			this.type = type;
			this.dayId = dayId;
		}

		String getHost() {
			return host;
		}

		void setHost(String host) {
			this.host = host;
		}

		String getPath() {
			return path;
		}

		void setPath(String path) {
			this.path = path;
		}

		String getQuery() {
			return query;
		}

		void setQuery(String query) {
			this.query = query;
		}

		String getBody() {
			return body;
		}

		void setBody(String body) {
			this.body = body;
		}

		Long getUserId() {
			return userId;
		}

		void setUserId(Long userId) {
			this.userId = userId;
		}

		String getRoleId() {
			return roleId;
		}

		void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		String getNamespace() {
			return namespace;
		}

		void setNamespace(String namespace) {
			this.namespace = namespace;
		}

		String getAction() {
			return action;
		}

		void setAction(String action) {
			this.action = action;
		}

		String getCode() {
			return code;
		}

		void setCode(String code) {
			this.code = code;
		}

		String getMethod() {
			return method;
		}

		void setMethod(String method) {
			this.method = method;
		}

		String getLevel() {
			return level;
		}

		void setLevel(String level) {
			this.level = level;
		}

		String getCreateTime() {
			return createTime;
		}

		void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		String getStatus() {
			return status;
		}

		void setStatus(String status) {
			this.status = status;
		}

		String getTraceId() {
			return traceId;
		}

		void setTraceId(String traceId) {
			this.traceId = traceId;
		}

		String getType() {
			return type;
		}

		void setType(String type) {
			this.type = type;
		}

		String getDayId() {
			return dayId;
		}

		void setDayId(String dayId) {
			this.dayId = dayId;
		}

		@Override
		public String toString() {
			return "ApiLog{" +
					"host='" + host + '\'' +
					", path='" + path + '\'' +
					", query='" + query + '\'' +
					", body='" + body + '\'' +
					", userId=" + userId +
					", roleId='" + roleId + '\'' +
					", namespace='" + namespace + '\'' +
					", action='" + action + '\'' +
					", code='" + code + '\'' +
					", method='" + method + '\'' +
					", level='" + level + '\'' +
					", createTime='" + createTime + '\'' +
					", status='" + status + '\'' +
					", traceId='" + traceId + '\'' +
					", type='" + type + '\'' +
					", dayId='" + dayId + '\'' +
					'}';
		}
	}

}
