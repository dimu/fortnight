/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import static org.apache.flink.table.api.Expressions.$;

/**
 * Simple example for demonstrating the use of SQL on a Stream Table in Java.
 *
 * <p>Usage: <code>StreamSQLExample --planner &lt;blink|flink&gt;</code><br>
 *
 * <p>This example shows how to:
 *  - Convert DataStreams to Tables
 *  - Register a Table under a name
 *  - Run a StreamSQL query on the registered Table
 *
 */
public class KafkaStreamAddColumn {

	// *************************************************************************
	//     PROGRAM
	// *************************************************************************

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

		//flink支持嵌套结构的定义，参考 https://blog.csdn.net/weixin_35949435/article/details/113084930?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs&dist_request_id=&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.baidujs
		TableResult sourceTable = tEnv.executeSql("CREATE TABLE dwx_product_source ("
				+ "pid    VARCHAR,"
				+ "projectId    VARCHAR,"
				+ "data ROW(`id` bigint, version string, params Map<String,Map<String,String>>),"
//				+ "`inner` ROW(`idid` BIGINT),"
				+ "`inner` MAP<String,String>,"
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
		System.out.println(sourceTable.getTableSchema().toString());
//		tEnv.from()

//				tEnv.executeSql("CREATE TABLE fs_table ("
//						+ "idid bigint,"
//						+ "id bigint"
//				+ ") WITH ("
//				+ "'connector'     = 'kafka',"
//				+ "'topic'    = 'bdsDatapush2',"
//				+ "'scan.startup.mode' = 'latest-offset',"
//				+ "'properties.bootstrap.servers' = '10.12.31.148:9092,10.12.31.149:9092,10.12.31.150:9092',"
//				+ "'format' = 'json'"
//				+ ")");



//		TableResult fileSink = tEnv.executeSql("insert into fs_table SELECT idid, sum(id) as id FROM dwx_product_source group by idid");
		Table tableselect = tEnv
				.sqlQuery("SELECT `inner`['idid'] AS idid, user_action_time as startTime,data FROM dwx_product_source");
//		fileSink.print();data
		//窗口计算才能用append,table的查询，过滤与条件语句功能强大

		tableselect = tableselect.addColumns($("data['params']['$123']['value']").as("$123"));
		tableselect  = tableselect.filter($("$123").isGreater(10));
//		Table filterTable = tableselect.select("*");
		System.out.println(Arrays.asList(tableselect.getSchema().getFieldNames()).stream()
				.collect(Collectors.joining(",")));
//		tEnv.toAppendStream(filterTable, Row.class).print();
		//
		tEnv.toRetractStream(tableselect, Row.class).print();
		//		tEnv.toAppendStream()
		env.execute("StreamSQLTest");
	}
}
