package dwx.tech.res.flink.stream;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class LightweightAnalysis1 {

	public static void main(String[] args) throws Exception {

		final ParameterTool params = ParameterTool.fromArgs(args);
		String planner = params.has("planner") ? params.get("planner") : "blink";

		// set up execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		StreamTableEnvironment tEnv;
		if (Objects.equals(planner, "blink")) {
			System.out.println("use blink planner engine");
			// use blink planner in streaming mode
			EnvironmentSettings settings = EnvironmentSettings.newInstance()
					.inStreamingMode()
					.useBlinkPlanner()
					.build();
			tEnv = StreamTableEnvironment.create(env, settings);
		}
		else if (Objects.equals(planner, "flink")) {
			// use flink planner in streaming mode
			System.out.println("use flink planner engine");
			EnvironmentSettings settings = EnvironmentSettings.newInstance()
					.inStreamingMode()
					.useOldPlanner()
					.build();
			tEnv = StreamTableEnvironment.create(env, settings);
		}
		else {
			System.err.println("The planner is incorrect. Please run 'StreamSQLExample --planner <planner>', " +
					"where planner (it is either flink or blink, and the default is blink) indicates whether the " +
					"example uses flink planner or blink planner.");
			return;
		}

		TableResult propertyKafka = tEnv.executeSql("CREATE TABLE device_property ("
				+ "`inner` ROW(`ipid` BIGINT,`idid` BIGINT,`traceId` VARCHAR,`spanId` VARCHAR),"
				+ "messageType    VARCHAR,"
				+ "notifyType varchar,"
				+ "pid varchar,"
				+ "uid varchar,"
				+ "devName varchar,"
				+ "gateway varchar,"
				+ "data string,"
				+ "projectId varchar,"
				+ " processTime AS PROCTIME() "
				+ ") WITH ("
				+ "'connector'     = 'kafka',"
				+ "'topic'    = 'tm-event',"
				+ "'scan.startup.mode' = 'latest-offset',"
				+ "'properties.group.id' = 'flink-group1',"
				+ "'json.fail-on-missing-field' = 'false',"
				+ "'properties.bootstrap.servers' = '10.12.31.22:9092,10.12.31.23:9092,10.12.31.24:9092',"
				+ "'format' = 'json' "
				+ ")");

//		TableResult fileSink = tEnv.executeSql("insert into fs_table SELECT idid, sum(id) as id FROM dwx_product_source group by idid");
//		Table tableselect = tEnv
//				.sqlQuery("SELECT pid AS pid, user_action_time as startTime,cast(data['params']['$123']['value'] as bigint) as $123 FROM dwx_product_source");

		Table devicePropertyTable = tEnv
				.sqlQuery("SELECT `inner`.ipid as ipid, `inner`.idid as idid, `inner`.traceId as trace_id, `inner`.spanId as span_id,"
						+ " messageType as message_type, notifyType as notify_type, pid, cast(uid as BIGINT) as uid, "
						+ " devName as dev_name, gateway, data, projectId as pj_id,"
						+ "DATE_FORMAT(LOCALTIMESTAMP, 'yyyyMMdd') as day_id FROM device_property");

		String sinkTableSql = "CREATE TABLE SINKTABLE ("
				+ "  ipid bigint,"
				+ "  idid bigint,"
				+ "  trace_id string,"
				+ "  span_id string,"
				+ "  message_type string,"
				+ "  notify_type string,"
				+ "  pid string,"
				+ "  uid bigint,"
				+ "  dev_name string,"
				+ "  gateway string,"
				+ "  data string,"
				+ "  pj_id string,"
				+ "  day_id string"
				+ ") WITH ("
				+ "   'connector' = 'jdbc',"
				+ "   'url' = 'jdbc:mysql://172.19.3.47:3306/mongo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT%2B8&useSSL=false',"
				+ "   'table-name' = 'device_func_ext_studio',"
				+ "   'username' = 'mongo',"
				+ "   'password' = 'mongo_test'"
				+ ")";
//		"insert into SINKTABLE select pid, $123 from dwx_product_source where $123 > 10";
		//创建mysql sink table
		tEnv.executeSql(sinkTableSql);
		System.out.println(Arrays.asList(devicePropertyTable.getSchema().getFieldNames()).stream()
				.collect(Collectors.joining(",")));


//		tEnv.executeSql("insert into SINKTABLE SELECT `inner`.ipid as ipid, `inner`.idid as idid, `inner`.traceId as trace_id, `inner`.spanId as span_id,"
//						+ " messageType as message_type, notifyType as notify_type, pid, cast(uid as BIGINT) as uid, "
//						+ " devName as dev_name, gateway as gateway, data as data, projectId as pj_id,"
//						+ "DATE_FORMAT(LOCALTIMESTAMP, 'yyyyMMdd') as day_id FROM device_property");
		tEnv.toAppendStream(devicePropertyTable, Row.class).print();
		//query schema必须与sink schema一致
//		TableResult tableResult = tableselect.select($("pid"), $("$123"), $("startTime")).as("pid,$123,$startTime").executeInsert("SINKTABLE");
//		tEnv.toRetractStream(tableselect, Row.class).print();
		//		tEnv.toAppendStream()
//		System.out.println(tableResult.getJobClient().get().getJobID());
		env.execute("StreamSQLTest");
	}
}
