package concurrency.notifythread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 实现等待与通知效果
 * @author dwx
 */
public class WaitNotify {


    /**
     * 锁对象
     */
    static Object lock = new Object();

    /**
     * 条件控制
     */
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new WaitingThread(), "waiting thread").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(new NotifyThread(), "notify thread").start();
    }

    /**
     * waiting thread
     */
    static class WaitingThread implements Runnable {

        @Override
        public void run() {
            /**
             * 获取对象的锁
             */
            synchronized (lock) {
                while(flag) {
                    System.out.println(Thread.currentThread() + " wait at " + new Date());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread() + " flag is false. finish task  at " + new Date());
            }

        }
    }

    static  class  NotifyThread implements  Runnable {

        @Override
        public void run() {
            /**
             * 获取对象锁
             */
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock, notify at " + new Date());
                lock.notify();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again  at " + new Date());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
