package concurrency.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * add AQS res
 * @author dwx
 */
public class AQSTest {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " begin to acquire lock");

                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
                    System.out.println("lock is held by current thread : " +    ((ReentrantLock)lock).isHeldByCurrentThread());
                    for (; ; ) {
                    }
                } finally {
                    lock.unlock();
                }

            }, "waiting thread " + i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.holdsLock(lock));
        /**
         * waiting queue has 9 waiters.
         */
        System.out.println( "waiting queue length:" +  ((ReentrantLock)lock).getQueueLength());
        System.out.println("lock is held by current thread : " +    ((ReentrantLock)lock).isHeldByCurrentThread());
    }

}
