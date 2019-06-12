package dwx.res.hadoop.hbase.repository;

import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

import dwx.res.hadoop.hbase.conf.HbaseConnectionFactory;
import dwx.res.hadoop.hbase.model.MroHW;


public class MroHWRepository {

	private static String tableName = "mro_hw";
	
	private static final byte[] sc = Bytes.toBytes("sc");
	
	private static final byte[] nc = Bytes.toBytes("nc");
	
	private static final byte[] lteScRSRP = Bytes.toBytes("LteScRSRP");
	
	/**
	 * insert mro entity 
	 * @param entity mro entity
	 * @throws IOException
	 */
	public static final void put(MroHW entity) throws IOException {
		Connection con  = HbaseConnectionFactory.getConnection();
		Table table = con.getTable(TableName.valueOf(tableName));
		
		Put put  = new Put(Bytes.toBytes(entity.getBtsId()+"_"+entity.getMmeUeS1apId()+"_"+ entity.getTimeStamp()));
		put.addColumn(sc, lteScRSRP, Bytes.toBytes(entity.getLteScRSRP()));
		table.put(put);
		table.close();
//		con.close();
	}
}
