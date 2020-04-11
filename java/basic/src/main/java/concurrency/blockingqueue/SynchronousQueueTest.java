package concurrency.blockingqueue;

import java.util.concurrent.*;

/**
 * SynchronousQueue不存储元素的阻塞队列，每个put操作必须等take操作后才能够继续添加
 * @author dwx
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        int count_size = 10;

        Runnable producer = () -> {
            int count = 0;
            while (count++ < count_size) {
                int producedElment = ThreadLocalRandom.current().nextInt();
                try {
                    queue.put(producedElment);
                    System.out.println("produce element: " + producedElment);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            int count = 0;
            while(count++ < count_size) {
                try {
                    int consumedElement = queue.take();
                    System.out.println("consume element： " + consumedElement);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.submit(producer);
        executorService.submit(consumer);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();


    }
}
