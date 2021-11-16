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

import static org.apache.flink.table.api.Expressions.$;

public class KafkaStreamGroupSinkMysql {

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

		TableResult sourceTable = tEnv.executeSql("CREATE TABLE dwx_product_source ("
				+ "pid    VARCHAR,"
				+ "projectId    VARCHAR,"
				+ "uid varchar,"
				+ "devName,"
				+ " user_action_time AS PROCTIME() "
				+ ") WITH ("
				+ "'connector'     = 'kafka',"
				+ "'topic'    = 'tm-property',"
				+ "'scan.startup.mode' = 'latest-offset',"
				+ "'properties.group.id' = 'flink-group',"
				+ "'json.fail-on-missing-field' = 'false',"
				+ "'properties.bootstrap.servers' = '10.12.31.22:9092,10.12.31.23:9092,10.12.31.24:9092',"
				+ "'format' = 'json' "
				+ ")");

//		TableResult fileSink = tEnv.executeSql("insert into fs_table SELECT idid, sum(id) as id FROM dwx_product_source group by idid");
		Table tableselect = tEnv
				.sqlQuery("SELECT pid AS pid, user_action_time as startTime,cast(data['params']['$123']['value'] as bigint) as $123 FROM dwx_product_source");

		String sinkTableSql = "CREATE TABLE SINKTABLE ("
				+ "  pid STRING,"
				+ "  $123 bigint,"
				+ "  $startTime timestamp"
				+ ") WITH ("
				+ "   'connector' = 'jdbc',"
				+ "   'url' = 'jdbc:mysql://172.19.3.160:3307/dwx_test',"
				+ "   'table-name' = 'sink_test',"
				+ "   'username' = 'dengwx',"
				+ "   'password' = 'Dwx!1116'"
				+ ")";
//		"insert into SINKTABLE select pid, $123 from dwx_product_source where $123 > 10";
		tEnv.executeSql(sinkTableSql);
		System.out.println(Arrays.asList(tableselect.getSchema().getFieldNames()).stream()
				.collect(Collectors.joining(",")));
//		tEnv.toAppendStream(filterTable, Row.class).print();
		//query schema必须与sink schema一致
		TableResult tableResult = tableselect.select($("pid"), $("$123"), $("startTime")).as("pid,$123,$startTime").executeInsert("SINKTABLE");
//		tEnv.toRetractStream(tableselect, Row.class).print();
		//		tEnv.toAppendStream()
		System.out.println(tableResult.getJobClient().get().getJobID());
//		env.execute("StreamSQLTest");
	}
}
