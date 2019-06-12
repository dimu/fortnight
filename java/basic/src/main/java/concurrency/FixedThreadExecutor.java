package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadExecutor {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(3);
//		ExecutorService exec = Executors.newFixedThreadPool(1);
		for(int i = 0; i < 5; i++){
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}

}
