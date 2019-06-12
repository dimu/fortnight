package dwx.res.hadoop.hbase.mrunion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableSplit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;

/**
 * chr与mr关联，chr按照imis+mdn+timestamp作为主键
 * mr以enode+ue+timestatmp作为主键，两表连接通过enode+ue+timestamp来关联
 * 
 * 初步需要使用MultiTableInputFormat类作为job的输入源，然后通过mapreduce来输出
 * MRMapper泛型输入为
 * @author dwx
 * 2016年11月9日
 *
 */
public class MRMapper extends TableMapper<Text, Put>{
	
	//mapper输出的key值
	Text mapperKey;
	
	//map的输出为MapWritable对象
	MapWritable mapperValue;
	
	//chr ts
	String chrTs;
	
	//enodeb
	byte[] enodeb;
	
	//ue
	byte[] ue;
	
	//chr imis号
	String imis;
	
	//chr mdn号
	String mdn;
	
	/**
	 * 聚合mr和chr
	 */
	@Override
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context) {
		// get table name
		TableSplit currentSplit = (TableSplit)context.getInputSplit();
		byte[] tableName = currentSplit.getTableName();
		String chrTsTime;
		try {
			//解析chr数据
			if (Arrays.equals(tableName, MRJob.CHR_TABLE)) {
				//分割rowkey：imsi+mdn+timstamp
				String[] keyarray = new String(rowKey.get()).split("_");
				imis = keyarray[0];
				mdn = keyarray[1];
				chrTs = keyarray[2];
				
				//如果是imis或者mdn中
				if (imis.equalsIgnoreCase("FFFFFFFFFFFFFFFF") || mdn.equalsIgnoreCase("FFFFFFFFFFFFFFFF")) {
					return;
				}
				
				//如果没有时间戳的数据也是无效数据
				if (chrTs == null || chrTs.isEmpty()) {
					return;
				}
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				chrTsTime = String.valueOf(df.parse(chrTs).getTime());
				//获取enodeb
				enodeb = columns.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("enode"));
				
				//获取ue
				ue = columns.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("pci"));
				
				//构成主键方式
				mapperKey = new Text(new String(enodeb) + "_" + new String(ue) + "_" + chrTsTime);
				
				//context.getCounter(counterName)
				//写入reducer,结果为text，result,，必须用copy
				Put put = new Put(mapperKey.copyBytes());
		 		for (Cell kv : columns.rawCells()) {
					put.add(new KeyValue(mapperKey.copyBytes(), CellUtil.cloneFamily(kv), CellUtil.cloneQualifier(kv), CellUtil.cloneValue(kv)));
				}
		 		Cell imisCell = new KeyValue(mapperKey.copyBytes(), Bytes.toBytes("cf1"), Bytes.toBytes("imis"), imis.getBytes());
		 		put.add(imisCell);
		 		Cell mdnCell = new KeyValue(mapperKey.copyBytes(), Bytes.toBytes("cf1"), Bytes.toBytes("mdn"), mdn.getBytes());
		 		put.add(mdnCell);
				context.write(mapperKey, put);
			} else if (Arrays.equals(tableName, MRJob.MR_TABLE)) {
				Put put = new Put(rowKey.get());
		 		for (Cell kv : columns.rawCells()) {
					put.add(kv);
				}
		 		context.write(new Text(rowKey.toString()), put);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
