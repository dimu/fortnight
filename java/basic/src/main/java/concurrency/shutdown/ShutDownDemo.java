package concurrency.shutdown;

import java.util.concurrent.TimeUnit;

/**
 * 如何安全的中断线程，常用的两种方式
 * 1. 使用中断
 * 2. 使用boolean改变循环状态，来控制程序的退出
 * 如何优雅的退出线程非常重要，避免线程一直不退出，导致cpu利用率过高
 * @author dwx
 */
public class ShutDownDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runner(), "thread1");
        t1.start();
        /**
         * main thread 休眠1秒
         */
        TimeUnit.SECONDS.sleep(1);

        /**
         * 唤醒t1线程
         */
        t1.interrupt();

        Runner two  = new Runner();
        t1 = new Thread(two, "thread2");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        two.cancel();


    }

    /**
     * 需要终止的线程任务
     */
    private static class  Runner implements Runnable {

        private long i;

        private volatile boolean on = true;

        @Override
        public void run() {
            /**
             * 通过中断标记位与boolean状态来控制线程退出
             */
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }

            System.out.println("thread " + Thread.currentThread().getName() + " exit, Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
