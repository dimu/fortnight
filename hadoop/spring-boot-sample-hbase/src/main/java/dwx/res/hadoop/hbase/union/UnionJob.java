package dwx.res.hadoop.hbase.union;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

/**
 * two hbase table union operation
 * @author dwx
 * 2016年11月7日
 *
 */
public class UnionJob extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		List<Scan> scans = new ArrayList<Scan>();

		/**
		 * default fully table scan, set table.name attribute 
		 */
		Scan scan1 = new Scan();
		scan1.setAttribute("scan.attributes.table.name", Bytes.toBytes("storeSales"));
		System.out.println(scan1.getAttribute("scan.attributes.table.name"));
		scans.add(scan1);

		Scan scan2 = new Scan();
		scan2.setAttribute("scan.attributes.table.name", Bytes.toBytes("onlineSales"));
		System.out.println(scan2.getAttribute("scan.attributes.table.name"));
		scans.add(scan2);

		Configuration conf = HBaseConfiguration.create();
//		conf.set("mapred.job.tracker", "centos22:9001");
//		conf.set("fs.default.name", "hdfs://centos22:9000");
		conf.set("hbase.zookeeper.quorum", "centos22,slave21,slave26");
		conf.set("hbase.zookeeper.property.clientPort","2181");
//		Configuration conf = new Configuration();
//		HBaseConfiguration.addHbaseResources(conf);
//		conf.addResource("mapred-default.xml");
//		conf.addResource("mapred-site.xml");
		Job job = Job.getInstance(conf);
		job.setJarByClass(UnionJob.class);

		/**
		 * initTableMapperJob(List<Scan> scans, Class<? extends TableMapper> mapper, Class<?> outputKeyClass, Class<?> outputValueClass, Job job)
		 * init mapper job, input scans, mapper类, outputkey, outputvalue, job
		 */
		TableMapReduceUtil.initTableMapperJob(scans, UnionMapper.class, Text.class, IntWritable.class, job);
		
		/**
		 * initTableReducerJob(String table, Class<? extends TableReducer> reducer, Job job)
		 * table table name: reducer output value table
		 * reducer TableReducer class name: mappper reducer class
		 * Job job value:the job excutor
		 */
		TableMapReduceUtil.initTableReducerJob("totalSales", UnionReducer.class, job);
		
		job.waitForCompletion(true);
		return 0;
	}

//	public static void main(String[] args) throws Exception {
//		UnionJob runJob = new UnionJob();
//		runJob.run(args);
//	}
}