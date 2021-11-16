//package dwx.tech.res.flink.connectors;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DBObject;
//import com.mongodb.hadoop.io.BSONWritable;
//import com.mongodb.hadoop.mapred.MongoInputFormat;
//import com.mongodb.hadoop.mapred.MongoOutputFormat;
//import com.mongodb.hadoop.util.MongoConfigUtil;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.java.DataSet;
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.api.java.hadoop.mapred.HadoopInputFormat;
//import org.apache.flink.api.java.hadoop.mapred.HadoopOutputFormat;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.table.api.Table;
//import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;
//import org.apache.flink.types.Row;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.JobConf;
//import org.bson.BSONObject;
//
//import static org.apache.flink.table.api.Expressions.$;
//
//public class MongodbConnectorExample {
//
//	public static void main(String[] args) throws Exception {
//
//		// set up the execution environment
//		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//		BatchTableEnvironment  bEnv = BatchTableEnvironment.create(env);
//
//		// create a MongodbInputFormat, using a Hadoop input format wrapper
//		HadoopInputFormat<BSONWritable, BSONWritable> hdIf =
//				new HadoopInputFormat<BSONWritable, BSONWritable>(new MongoInputFormat(),
//						BSONWritable.class, BSONWritable.class,	new JobConf());
//
//		// specify connection parameters
//		hdIf.getJobConf().set("mongo.input.uri",
//				"mongodb://cmd_admin:Qh68_9l9M_gJ55@10.12.31.34:27019/db_cmd_storage.st_device_cmd");
//
//		DataSet<Tuple2<BSONWritable, BSONWritable>> input = env.createInput(hdIf);
//		// a little example how to use the data in a mapper.
//		DataSet<Tuple2< Long, BSONWritable>> fin = input.map(
//				new MapFunction<Tuple2<BSONWritable, BSONWritable>,
//										Tuple2<Long,BSONWritable> >() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<Long,BSONWritable> map(
//							Tuple2<BSONWritable, BSONWritable> record) throws Exception {
//						BSONWritable value = record.getField(1);
//						BSONObject doc = value.getDoc();
////						BasicDBObject jsonld = (BasicDBObject) doc.get("jsonld");
//
////						String id = jsonld.getString("@id");
//						String cmid = (String)doc.get("cmid");
//						Long idid = (long)doc.get("idid");
//						DBObject builder = BasicDBObjectBuilder.start()
//								.add("cmid", cmid)
//								.add("idid", idid)
//								.get();
//
//						BSONWritable w = new BSONWritable(builder);
//						return new Tuple2<Long,BSONWritable>(idid, w);
//					}
//				});
//
//
//		Table table = bEnv.fromDataSet(fin);
//		table = table.filter($("f0").isEqual(307666));
//		table.window();
//		// emit result (this works only locally)
//		bEnv.toDataSet(table, Row.class).print();
//
//
//		// create a MongodbInputFormat, using a Hadoop input format wrapper
//		HadoopInputFormat<BSONWritable, BSONWritable> deviceInput =
//				new HadoopInputFormat<>(new MongoInputFormat(),
//						BSONWritable.class, BSONWritable.class,	new JobConf());
//
//		// specify connection parameters
//		deviceInput.getJobConf().set("mongo.input.uri",
//				"mongodb://db_dm_manager:E8i8_p80I_Dy32@172.19.19.107:27019,172.19.19.108:27019,172.19.19.109:27019/db_dm.device");
//		DataSet<Tuple2<BSONWritable, BSONWritable>> inputDevice = env.createInput(hdIf);
//		DataSet<Tuple4< Long, BSONWritable>> finDevice = inputDevice.map(
//				new MapFunction<Tuple2<BSONWritable, BSONWritable>, Tuple2<Long,BSONWritable> >() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<Long,BSONWritable> map(
//							Tuple2<BSONWritable, BSONWritable> record) throws Exception {
//						BSONWritable value = record.getField(1);
//						BSONObject doc = value.getDoc();
////						BasicDBObject jsonld = (BasicDBObject) doc.get("jsonld");
//
////						String id = jsonld.getString("@id");
//						String cmid = (String)doc.get("cmid");
//						Long idid = (long)doc.get("idid");
//						DBObject builder = BasicDBObjectBuilder.start()
//								.add("cmid", cmid)
//								.add("idid", idid)
//								.get();
//
//						BSONWritable w = new BSONWritable(builder);
//						return new Tuple4<Long,BSONWritable>(idid, w);
//					}
//				});
////		fin.print();
//
////		MongoConfigUtil.setOutputURI( hdIf.getJobConf(),
////				"mongodb://localhost:27017/test.testData");
////		// emit result (this works only locally)
////		fin.output(new HadoopOutputFormat<Text,BSONWritable>(
////				new MongoOutputFormat<Text,BSONWritable>(), hdIf.getJobConf()));
//
//		// execute program
////		env.execute("Mongodb Example");
//	}
//}
