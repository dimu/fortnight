package dwx.res.hadoop.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author dwx
 * 2016年11月4日
 *
 */
public class HadoopMapReduceApp {
	
	public static void main(String[] args) {
		final Logger log = LoggerFactory.getLogger(HadoopMapReduceApp.class);
		
		String s = "123";
		
		if (log.isDebugEnabled()) {
			log.info("log debug level enable  {}", s);
		}
	}
}
