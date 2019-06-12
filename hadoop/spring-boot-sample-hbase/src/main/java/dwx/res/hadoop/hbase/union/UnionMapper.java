package dwx.res.hadoop.hbase.union;

import java.util.Arrays;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableSplit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/**
 *  
 * @author dwx
 * 2016年11月7日
 *
 */
public class UnionMapper extends TableMapper<Text, IntWritable> {
	
	//商场
	private static byte[] storeSalesTable = Bytes.toBytes("storeSales");
	
	//在线
	private static byte[] onlineSalesTable = Bytes.toBytes("onlineSales");
	byte[] sales;
	String storeSales;
	Integer sSales;
	String onlineSales;
	Integer oSales;
	Text mapperKey;
	IntWritable mapperValue;

	@Override
	public void map(ImmutableBytesWritable rowKey, Result columns, Context context) {
		// get table name
		TableSplit currentSplit = (TableSplit)context.getInputSplit();
		byte[] tableName = currentSplit.getTableName();
		try {
			//匹配线下商场，生成mapperKey为分割的主键，mapperValue为sSales字段的value
			if (Arrays.equals(tableName, storeSalesTable)) {
				String date = new String(rowKey.get()).split("#")[0];
				sales = columns.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("sSales"));
				storeSales = new String(sales);
				sSales = new Integer(storeSales);
				mapperKey = new Text("s#" + date);
				mapperValue = new IntWritable(sSales);
				context.write(mapperKey, mapperValue);
			//匹配线上商场
			} else if (Arrays.equals(tableName, onlineSalesTable)) {
				String date = new String(rowKey.get());
				sales = columns.getValue(Bytes.toBytes("cf2"), Bytes.toBytes("oSales"));
				onlineSales = new String(sales);
				Integer oSales = new Integer(onlineSales);
				mapperKey = new Text("o#"+date);
				mapperValue = new IntWritable(oSales);
				context.write(mapperKey, mapperValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}