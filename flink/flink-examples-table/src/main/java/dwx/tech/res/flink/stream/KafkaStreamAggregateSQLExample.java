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
public class KafkaStreamAggregateSQLExample {

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
		} else if (Objects.equals(planner, "flink")) {
			// use flink planner in streaming mode
			System.out.println("use flink planner engine");
			EnvironmentSettings settings = EnvironmentSettings.newInstance()
					.inStreamingMode()
					.useOldPlanner()
					.build();
			tEnv = StreamTableEnvironment.create(env, settings);
		} else {
			System.err.println("The planner is incorrect. Please run 'StreamSQLExample --planner <planner>', " +
				"where planner (it is either flink or blink, and the default is blink) indicates whether the " +
				"example uses flink planner or blink planner.");
			return;
		}

		TableResult sourceTable = tEnv.executeSql("CREATE TABLE dwx_product_source ("
				+ "pid    VARCHAR,"
				+ "projectId    VARCHAR,"
				+ "data ROW(`id` bigint),"
				+ "`inner` ROW(`idid` BIGINT),"
				+ " user_action_time AS PROCTIME() "
				+ ") WITH ("
				+ "'connector'     = 'kafka',"
				+ "'topic'    = 'tm-service',"
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

//		tEnv.executeSql("CREATE TABLE fs_table ("
//				+ "idid bigint,"
//				+ "id bigint"
//				+ ")  WITH ("
//				+ "'connector'='filesystem',"
//				+ "'path'='file:///d:/flink',"
//				+ "'format'='JSON')");

//		TableResult fileSink = tEnv.executeSql("insert into fs_table SELECT idid, sum(id) as id FROM dwx_product_source group by idid");
		Table tableselect = tEnv.sqlQuery("SELECT idid, TUMBLE_START(user_action_time, INTERVAL '1' MINUTE) as startTime,sum(id) as sumId FROM dwx_product_source  group  by idid, TUMBLE(user_action_time, INTERVAL '1' MINUTE)");
//		fileSink.print();
		//窗口计算才能用append,table的查询，过滤与条件语句功能强大
		Table filterTable  = tableselect.filter($("sumId").isGreater(10)).select($("startTime"), $("sumId")).as("first,second");
		System.out.println(Arrays.asList(filterTable.getSchema().getFieldNames()).stream()
				.collect(Collectors.joining(",")));
		tEnv.toAppendStream(filterTable, Row.class).print();
		//
//		tEnv.toRetractStream(tableselect, Row.class).print();
		//		tEnv.toAppendStream()
		env.execute("StreamSQLTest");



//		tEnv.executeSql("CREATE TABLE dwx_product_sink ("
//				+ "idx    BIGINT,"
//				+ "val    VARCHAR,"
//				+ "pushId VARCHAR"
//				+ ") WITH ("
//				+ "'connector'     = 'kafka',"
//				+ "'topic'    = 'bdsDatapush2',"
//				+ "'scan.startup.mode' = 'latest-offset',"
//				+ "'properties.bootstrap.servers' = '10.12.31.148:9092,10.12.31.149:9092,10.12.31.150:9092',"
//				+ "'format' = 'json'"
//				+ ")");
//
//		Table resourceTable = tEnv.from("dwx_product_source");

//		/**
//		 * insert语句执行后，会立即提交job，所以后面不用再提交了
//		 */
//		TableResult sinR = tEnv.executeSql("INSERT INTO dwx_product_sink "
//				+ "SELECT "
//				+ " idx,"
//				+ " val,"
//				+ "'729756828799336448' as pushId "
//				+ " FROM dwx_product_source");

//		System.out.println(env.getExecutionPlan());

//		tEnv.execute("table job!");
//		System.out.println("sink print!");
//		sinR.print();

//		tEnv.toAppendStream(dwx_product_sink., ProductSink.class);

//		Table result = tEnv.sqlQuery("SELECT "
//				+ " idx,"
//				+ " val,"
//				+ "'729756828799336448' as pushId "
//				+ " FROM dwx_product_source");
//
//		Table sinkTable = tEnv.from("dwx_product_sink");
//		TableResult tableResult = result.executeInsert("sinkTable", true);
//		tableResult.print();

		//convert datastream to table
//		Table resourceTable = tEnv.fromDataStream("dwx_product_source");
//		Table resourceTable = tEnv.from("dwx_product_source");
//
//		DataStream<Order> orderA = env.fromCollection(Arrays.asList(
//			new Order(1L, "beer", 3),
//			new Order(1L, "diaper", 4),
//			new Order(3L, "rubber", 2)));
//
//		DataStream<Order> orderB = env.fromCollection(Arrays.asList(
//			new Order(2L, "pen", 3),
//			new Order(2L, "rubber", 3),
//			new Order(4L, "beer", 1)));

//		// convert DataStream to Table
//		Table tableA = tEnv.fromDataStream(orderA, $("user"), $("product"), $("amount"));
//		// register DataStream as Table
//		tEnv.createTemporaryView("OrderB", orderB, $("user"), $("product"), $("amount"));
//
//		// union the two tables
//		Table result = tEnv.sqlQuery("SELECT * FROM " + tableA + " WHERE amount > 2 UNION ALL " +
//						"SELECT * FROM OrderB WHERE amount < 2");
//
//		tEnv.toAppendStream(result, Order.class).print();

		// after the table program is converted to DataStream program,
		// we must use `env.execute()` to submit the job.
//		System.out.println("print execution plan " + tEnv.ge());
//		env.execute("StreamSQLTest");
	}

	// *************************************************************************
	//     USER DATA TYPES
	// *************************************************************************

	/**
	 * Simple POJO.
	 */
	public static class Order {
		public Long user;
		public String product;
		public int amount;

		public Order() {
		}

		public Order(Long user, String product, int amount) {
			this.user = user;
			this.product = product;
			this.amount = amount;
		}

		@Override
		public String toString() {
			return "Order{" +
				"user=" + user +
				", product='" + product + '\'' +
				", amount=" + amount +
				'}';
		}
	}

	public static class ProductSource {
		public String pid;
		public Long idx;
		public String sn;
		public String val;

		String getPid() {
			return pid;
		}

		void setPid(String pid) {
			this.pid = pid;
		}

		Long getIdx() {
			return idx;
		}

		void setIdx(Long idx) {
			this.idx = idx;
		}

		String getSn() {
			return sn;
		}

		void setSn(String sn) {
			this.sn = sn;
		}

		String getVal() {
			return val;
		}

		void setVal(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return "ProductSource{" +
					"pid='" + pid + '\'' +
					", idx=" + idx +
					", sn='" + sn + '\'' +
					", val='" + val + '\'' +
					'}';
		}
	}

	public static class ProductSink{
		public Long idx;
		public String val;

		Long getIdx() {
			return idx;
		}

		void setIdx(Long idx) {
			this.idx = idx;
		}

		String getVal() {
			return val;
		}

		void setVal(String val) {
			this.val = val;
		}
	}

//	public static class GroupTest{
//		public Long idid;
//		public String val;
//
//		Long getIdx() {
//			return idx;
//		}
//
//		void setIdx(Long idx) {
//			this.idx = idx;
//		}
//
//		String getVal() {
//			return val;
//		}
//
//		void setVal(String val) {
//			this.val = val;
//		}
//	}
}
