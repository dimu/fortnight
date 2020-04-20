package concurrency.cyclicbarrier;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 假设一个excel中有多个sheet，统计最终的汇总，可以先分别
 * 统计各个sheet的值，
 *
 * @author dwx
 */
public class CyclicBarrierBankDemo implements Runnable {

    private final ConcurrentHashMap<String, Integer> countMap = new ConcurrentHashMap<>();
    private CyclicBarrier barrier = new CyclicBarrier(4, this);
    private Executor executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        CyclicBarrierBankDemo demo = new CyclicBarrierBankDemo();
        demo.count();
    }

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                countMap.put(Thread.currentThread().getName(), 1);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {

        AtomicInteger result = new AtomicInteger(0);

        countMap.forEach((key, item) -> {
            System.out.println("完成的线程" + key);
            result.addAndGet(item);
        });

        System.out.println("the result is: " + result.get());

    }
}
