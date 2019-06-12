package concurrency;

import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

/**
 * description: 并发执行线程
 *
 * @version 2017年4月5日 下午3:26:55
 * @see
 * modify content------------author------------date
 */
class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;

	/**
	 * 注入CountDownLatch对象
	 * CountDownLatchDemo.java constructor
	 * @param latch countdownlatch实例
	 */
	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		try {
			doWork();
			//并发类
			latch.countDown();
		} catch (InterruptedException ex) {
			// Acceptable way to exit
		}
	}

	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		print(this + "completed");
	}

	public String toString() {
		return String.format("%1$-3d ", id);
	}
}

/**
 * description: 等待队列
 *
 * @version 2017年4月5日 下午3:27:06
 * @see
 * modify content------------author------------date
 */
class WaitingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;

	WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		try {
			//需要等待上一步完成
			latch.await();
			print("Latch barrier passed for " + this);
		} catch (InterruptedException ex) {
			print(this + " interrupted");
		}
	}

	public String toString() {
		return String.format("WaitingTask %1$-3d ", id);
	}
}

/**
 * description: 场景用于需要
 *
 * @version 2017年4月5日 下午3:29:40
 * @see
 * modify content------------author------------date
 */
public class CountDownLatchDemo {
	static final int SIZE = 100;

	public static void main(String[] args) throws Exception {
		//缓冲线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// All must share a single CountDownLatch object:
		CountDownLatch latch = new CountDownLatch(SIZE);
		
		for (int i = 0; i < 10; i++) {
			exec.execute(new WaitingTask(latch));
		}
		
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new TaskPortion(latch));
		}
		print("Launched all tasks");
		// Quit when all tasks complete
		exec.shutdown(); 
	}
} 
