//package concurrency;
//
//import java.util.concurrent.Callable;
//
///**
// * Runnable 是执行工作的独立任务，不返回任何值，如果希望在任务完成时
// * 能够返回一个值，就必须实现Callable接口而不是Runnable接口
// * @author Administrator
// *
// */
//public class TaskWithResult implements Callable<String>{
//
//	private int id;
//	
//	public TaskWithResult(int id){
//		this.id = id;
//	}
//	
//	@Override
//	public String call() throws Exception {
//		return "result of TaskWithResult " + id;
//	}
//}
