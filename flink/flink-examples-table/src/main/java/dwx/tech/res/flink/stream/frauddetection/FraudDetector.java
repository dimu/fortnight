//package dwx.tech.res.flink.stream.frauddetection;
//
//import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
//import org.apache.flink.util.Collector;
//
///**
// * 检测器
// *
// * @author dwx
// * @date 2020-08-11
// */
//public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {
//
//	private static final long serialVersionUID = 1L;
//
//	private static final double SMALL_AMOUNT = 1.00;
//	private static final double LARGE_AMOUNT = 500.00;
//	private static final long ONE_MINUTE = 60 * 1000;
//
//	@Override
//	public void processElement(
//			Transaction transaction,
//			Context context,
//			Collector<Alert> collector) throws Exception {
//
//		Alert alert = new Alert();
//		alert.setId(transaction.getAccountId());
//
//		collector.collect(alert);
//	}
//}