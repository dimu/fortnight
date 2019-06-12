package concurrency;

import java.util.concurrent.TimeUnit;

public class SimpleThread extends Thread {
	private int countDown = 5;
	//静态变量
	private static int threadCount = 0;

	public SimpleThread() {
		super(Integer.toString(++threadCount));
		start();
	}

	public synchronized String toString() {
		//当前线程休眠的时候，进行线程调度
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "#" + getName() + "(" + countDown + "), ";
	}

	public void run() {
		while (true) {
			System.out.print(this);
			if (--countDown == 0) {
				return;
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++)
			new SimpleThread();
	}
} 
