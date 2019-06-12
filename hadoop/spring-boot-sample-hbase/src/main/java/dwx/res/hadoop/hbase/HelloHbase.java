package dwx.res.hadoop.hbase;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HelloHbase {
	
	public static final String tableName = "test";
	public static final byte[] family = Bytes.toBytes("f");
	public static final byte[] qualifier = Bytes.toBytes("name");
	
	public static int count = 0;
	
	public static void main(String[] args) throws IOException, ParseException {
		
		//创建配置对象
		Configuration conf = HBaseConfiguration.create();
//		conf.set("hbase.zookeeper.quorum", "centos22,slave21,slave26");
//		conf.set("hbase.zookeeper.property.clientPort","2181");
		
		//创建连接
		Connection con = ConnectionFactory.createConnection(conf);
		Table table = con.getTable(TableName.valueOf(tableName));
		System.out.println(family);
		System.out.println(qualifier);
		
		//插入或者更新数据
		Put p = new Put(Bytes.toBytes("123"));
		p.addColumn(family, qualifier, Bytes.toBytes("tang"));
		
//		p.addColumn(family, Bytes.toBytes("lastname"), Bytes.toBytes("pen"));
//		p.addColumn(family, Bytes.toBytes("age"), Bytes.toBytes(21));
		table.put(p);
		
		//读取，获取数据
		Get g = new Get(Bytes.toBytes("123"));
		Result r = table.get(g);
		System.out.println("get:" + Bytes.toString(r.getRow()));
		g.addColumn(family, qualifier);
		r = table.get(g);
		System.out.println(r.listCells().size());
		
		try {
			Scan s = new Scan();
			System.out.println("scan before!");
			long before = System.currentTimeMillis();
			ResultScanner rs = table.getScanner(s);
			System.out.println("scan after!");
			for (final Result res : rs) {
				count++;
				List<Cell> list = res.getColumnCells(family, qualifier);
				for(Cell item : list) {
					System.out.println(Bytes.toString(CellUtil.cloneRow(item)));
					System.out.println(Bytes.toString(CellUtil.cloneValue(item)));
				}
			}
			long after = System.currentTimeMillis();
			System.out.println("count:" + count + " time cost:" + (after-before));
		} finally {
			table.close();
			con.close();
		}
	}
	
}

