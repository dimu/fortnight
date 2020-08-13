//package dwx.tech.res.flink.stream.frauddetection;
//
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
///**
// * 诈骗监测任务
// *
// * @author dwx
// * @date 2020-08-11
// */
//public class FraudDetectionJob {
//
//	public static void main(String[] args) throws Exception {
//		StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
//
//		/**
//		 * 数据源是模拟的交易
//		 */
//		DataStream<Transaction> transactions = streamExecutionEnvironment.addSource(new TransactionSource()).name("transactions");
//
//		DataStream<Alert> alerts = transactions.keyBy(Transaction::getAccountId)
//									.process(new FraudDetector())
//									.name("fraud-detector");
//
//		alerts.addSink(new AlertSink()).name("send-alerts");
//
//		streamExecutionEnvironment.execute("Fraud Detection");
//	}
//}
