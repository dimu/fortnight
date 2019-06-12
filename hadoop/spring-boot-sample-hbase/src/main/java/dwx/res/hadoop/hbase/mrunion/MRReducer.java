package dwx.res.hadoop.hbase.mrunion;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.Text;

/**
 * map reducer class to output mr chr data 
 * mr timestamp between chr starttime and endtime
 * @author dwx
 * 2016年11月14日
 *
 */
public class MRReducer extends TableReducer<Text, Put, ImmutableBytesWritable> {

	@Override
	public void reduce(Text key, Iterable<Put> values, Context context) {
		
		//each mr data produce time
		String mrTimeStamp;
		
		//each chr start time
		String chrStartTime;
		
		//each chr end time
		String chrEndTime;
		
		for (Put put : values) {
			try {
				
//				chrStartTime = CellUtil.cloneValue(put.get(TableConst.CF_CF1, TableConst.CQ_CHR_ST).get(0)).toString();
//				chrEndTime = CellUtil.cloneValue(put.get(TableConst.CF_CF1, TableConst.CQ_CHR_ET).get(0)).toString();
//				mrTimeStamp = CellUtil.cloneValue(put.get(TableConst.CF_SC, TableConst.CQ_MR_TS).get(0)).toString();
//				
//				//business process logical
//				if (mrTimeStamp.compareTo(chrStartTime) >= 0 && mrTimeStamp.compareTo(chrEndTime) <=0) {
//					context.write(new ImmutableBytesWritable(key.getBytes()), put);
//				}
				context.write(new ImmutableBytesWritable(key.getBytes()), put);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
