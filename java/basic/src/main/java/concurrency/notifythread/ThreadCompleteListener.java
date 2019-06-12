package concurrency.notifythread;

/**
 * 线程结束监听接口，每个需要主动推送的线程都需要实现此接口
 * @author dwx
 *
 */
public interface ThreadCompleteListener {
	
	//通知主线程本线程已经结束
	void notifyOfThreadComplete(final Thread thread);
}
