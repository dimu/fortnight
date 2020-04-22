package concurrency.threadpoolexecutor;

import java.util.concurrent.*;

/**
 * 线程池执行器研究
 *
 * @author dwx
 */
public class ThreadPoolExecutorTest {

    public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) {
        /**
         * executor provider execute(runnable interface) and submit(runnable or callable) methods
         */
        Future<String> task = executor.submit(()-> {
            System.out.println(Thread.currentThread().getName()+ " is going to sleep 2 seconds!");
            TimeUnit.SECONDS.sleep(5);
            return "abc";
        });

        try {
            /**
             * get result from future task
             */
            String result = task.get();
            System.out.println(result + " return from future task!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("shutdown ThreadPoolExecutor");
            executor.shutdown();
        }
    }
}
