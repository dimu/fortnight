package concurrency.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * add AQS res
 * @author dwx
 */
public class AQSTest {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " begin to acquire lock");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
                    for (; ; ) {
                    }
                } finally {
                    lock.unlock();
                }

            }, "waiting thread " + i).start();
        }
        System.out.println(Thread.holdsLock(lock));
    }

}
