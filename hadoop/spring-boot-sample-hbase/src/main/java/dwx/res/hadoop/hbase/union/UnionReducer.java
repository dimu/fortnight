package dwx.res.hadoop.hbase.union;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class UnionReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable>{
	 @Override
	 public void reduce(Text key, Iterable<IntWritable> values, Context context)  {
		 //如果主键是以s开始表示为线下
		 if (key.toString().startsWith("s")) {
			 Integer dayStoreSales = 0;
			 for (IntWritable storeSale : values) {
				 dayStoreSales = dayStoreSales + new Integer(storeSale.toString());
			 }
			 Put put = new Put(Bytes.toBytes(key.toString()));
			 put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("tSales"), Bytes.toBytes(dayStoreSales));
			 try {
				context.write(null, put);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 } else {
			 Integer dayStoreSales = 0;
			 for (IntWritable storeSale : values) {
				 dayStoreSales = dayStoreSales + new Integer(storeSale.toString());
			 }
			 Put put = new Put(Bytes.toBytes(key.toString()));
			 put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("tSales"), Bytes.toBytes(dayStoreSales));
			 try {
				context.write(null, put);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
}
