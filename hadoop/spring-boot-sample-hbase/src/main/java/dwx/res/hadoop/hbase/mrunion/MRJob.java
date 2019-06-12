package dwx.res.hadoop.hbase.mrunion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * two hbase table union operation
 * @author dwx
 * 2016年11月7日
 *
 */
public class MRJob extends Configured implements Tool {
	
	Logger log = LoggerFactory.getLogger(MRJob.class);
	
	public static byte[] CHR_TABLE = Bytes.toBytes("chr_mm");
	public static byte[] MR_TABLE = Bytes.toBytes("mro_hw_aggregate");

	@Override
	public int run(String[] args) throws Exception {
		List<Scan> scans = new ArrayList<Scan>();

		/**
		 * default fully table scan, set table.name attribute 
		 */ 
		Scan scan1 = new Scan();
		scan1.setAttribute("scan.attributes.table.name", CHR_TABLE);
		scan1.setCaching(1000);
		//set data scan starttime
		if (2 == args.length) {
			String startTime = args[0];
			Filter filter1 = new RowFilter(CompareOp.GREATER_OR_EQUAL, new RegexStringComparator(".*_" + startTime));
			scan1.setFilter(filter1);
		}
		scans.add(scan1);

		Scan scan2 = new Scan();
		scan2.setAttribute("scan.attributes.table.name", MR_TABLE);
		scan2.setCaching(1000);
		//set data scan endtime
		if (2 == args.length) {
			String endTime = args[1];
			Filter filter2 = new RowFilter(CompareOp.LESS, new RegexStringComparator(".*_" + endTime));
			scan1.setFilter(filter2);
		}
		scans.add(scan2);

		Configuration conf = HBaseConfiguration.create();
		conf.set("mapred.job.tracker", "centos22:9001");
		conf.set("fs.default.name", "hdfs://centos22:9000");
		conf.set("hbase.zookeeper.quorum", "centos22,slave21,slave26");
		conf.set("hbase.zookeeper.property.clientPort","2181");
		Job job = Job.getInstance(conf);
		job.setJarByClass(MRJob.class);

		/**
		 * initTableMapperJob(List<Scan> scans, Class<? extends TableMapper> mapper, Class<?> outputKeyClass, Class<?> outputValueClass, Job job)
		 * init mapper job, input scans, mapper类, outputkey, outputvalue, job
		 */
		log.info("Start hbase map job {}", Calendar.getInstance().toString());
		TableMapReduceUtil.initTableMapperJob(scans, MRMapper.class, Text.class, Put.class, job);
		
		/**
		 * initTableReducerJob(String table, Class<? extends TableReducer> reducer, Job job)
		 * table table name: reducer output value table
		 * reducer TableReducer class name: mappper reducer class
		 * Job job value:the job excutor
		 */
		log.info("Start hbase reduce job {}", Calendar.getInstance().toString());
		TableMapReduceUtil.initTableReducerJob("chr_mr_aggregate", MRReducer.class, job);
		
		job.waitForCompletion(true);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		MRJob mrJob = new MRJob();
		mrJob.run(args);
	}
}