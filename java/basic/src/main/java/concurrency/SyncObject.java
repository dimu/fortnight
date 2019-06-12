package concurrency;

import static net.mindview.util.Print.*;

import java.util.concurrent.TimeUnit;

class DualSynch {
	private Object syncObject = new Object();

	public synchronized void f() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			print("f()");
			Thread.yield();
			TimeUnit.SECONDS.sleep(3);
		}
	}

	public void g() throws InterruptedException {
		synchronized (syncObject) {
			for (int i = 0; i < 5; i++) {
				print("g()");
				Thread.yield();
				TimeUnit.SECONDS.sleep(3);
			}
		}
	}
}

public class SyncObject {
	public static void main(String[] args) throws InterruptedException {
		final DualSynch ds = new DualSynch();
		new Thread() {
			public void run() {
				try {
					ds.f();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		ds.g();
	}
} /*
	 * Output: (Sample) g() f() g() f() g() f() g() f() g() f()
	 */// :~
