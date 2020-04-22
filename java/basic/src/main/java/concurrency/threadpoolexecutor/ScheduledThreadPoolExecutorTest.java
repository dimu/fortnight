package concurrency.threadpoolexecutor;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 调度线程池执行器研究
 *
 * @author dwx
 */
public class ScheduledThreadPoolExecutorTest {

    public static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(1);
        executor.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + " running times: " + i.getAndIncrement());
        }, 5, 2, TimeUnit.SECONDS);

        AtomicInteger count = new AtomicInteger(1);
        ScheduledFuture<?> ret = executor.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + " running times: " + count.getAndIncrement());}
        , 0, 2, TimeUnit.SECONDS);

//        while (true) {
//            System.out.println("延迟毫秒数" + ret.getDelay(TimeUnit.MILLISECONDS));
//        }

        executor.scheduleAtFixedRate(() -> {
                    /**
                     * command 3 execute time exceed the interval
                     */
                    System.out.println(Thread.currentThread().getName() + " add the third thread！ ");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                , 0, 2, TimeUnit.SECONDS);
    }
}
