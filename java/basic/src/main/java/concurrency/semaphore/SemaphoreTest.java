package concurrency.semaphore;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.*;

/**
 * 理解信号灯的使用场景，主要控制各个线程同时访问公共资源的数量
 * 例如：数据库连接只能同时并发10，如果连接的线程数过多，则容易造成
 * 无法获取连接异常，这时候可以用到信号灯
 */
public class SemaphoreTest {

    /**
     * 信号灯数量为10
     */
    private static final Semaphore semaphore = new Semaphore(10);

    /**
     * 任务线程为30个，大于信号灯的数量，每次只能够有10个线程能够执行
     */
    private static final ExecutorService executors = Executors.newFixedThreadPool(30);

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            executors.execute(()-> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " is running");
                    TimeUnit.MILLISECONDS.sleep(SecureRandom.getInstance("SHA1PRNG").nextInt(10));
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            });

        }

        /**
         * semaphore 暴露了一些获取等待线程，可用许可证等方法，可以坐更细致的控制
         */
        while (semaphore.hasQueuedThreads()) {
            System.out.println("waiting thread: " + semaphore.getQueueLength());
        }

        executors.shutdown();
    }

}
