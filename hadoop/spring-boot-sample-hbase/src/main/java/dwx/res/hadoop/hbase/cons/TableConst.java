package dwx.res.hadoop.hbase.cons;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * definition const value about column family and column qualifier
 * @author dwx
 * 2016年11月14日
 */
public class TableConst {
	
	//chr_mr_aggregate_dwx column family "cf1"
	public static final byte[] CF_CF1 = Bytes.toBytes("cf1");
	
	public static final byte[] CF_SC = Bytes.toBytes("sc");
	
	public static final byte[] CQ_CHR_ST = Bytes.toBytes("chrst");
	
	public static final byte[] CQ_CHR_ET = Bytes.toBytes("chret");
	
	public static final byte[] CQ_MR_TS = Bytes.toBytes("mrts");
}
