package concurrency.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 栅栏demo研究
 * @author dwx
 */
public class CyclicBarrierDemo {

    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                /**
                 * 运行到节点后，该线程立即停止，等待所有线程到达节点，再唤起
                 */
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                /**
                 * 第二次所有栅栏到达
                 */
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum,()->
                /**
                 * 所有线程执行完成后唤起的线程，相当于回调
                 */
                System.out.println(Thread.currentThread().getName() + " 完成最后任务")

        );

        for (int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
        }

//        TimeUnit.SECONDS.sleep(1);
//
//        barrier.reset();
    }

}