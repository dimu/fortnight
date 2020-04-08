package concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition Interface
 * @author dwx
 */
public class ConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ConditionUseCase r1 = new ConditionUseCase();
        ConditionUseCase r2 = new ConditionUseCase();
        Thread t1 = new Thread(()->{
            try {
                r1.conditionWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread one");
        t1.start();

        Thread t2 = new Thread(()->{

            t1.interrupt();
            try {
                /**
                 * if lock modifier is not shared between class, we can just use
                 * r1
                 */
                r2.conditionSignal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread two");
        t2.start();

    }

}

/**
 * class
 */
class ConditionUseCase{
    /**
     * use static modifier can share lock & condition between different instance
     */
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();


    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " begin to wait");
            /**
             * not response to thread interrupt
             */
            condition.awaitUninterruptibly();
            System.out.println(Thread.currentThread().getName() + " recover from wait");
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + condition + " begin to wait");
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + condition + " signal all over! ");
        } finally {
            lock.unlock();
        }
    }

}
