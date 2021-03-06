package concurrency.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * exchanger 用于两个线程之间交换数据，通过exchange()方法同步点来交换数据，应用场景
 * 数据核对等3
 *
 * @author dwx
 */
public class ExchangerTest {

    public static final Exchanger<String> exchanger = new Exchanger<>();

    public static final ExecutorService executors = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        executors.execute(() -> {
            try {
                /**
                 * 交换数据123, 获取另一个线程返回的数据，交换与返回遵循happens-before原则
                 */
                String b = exchanger.exchange("123");
                System.out.println("thread A get: " + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        executors.execute(() -> {
            try {
                String a = exchanger.exchange("321");
                System.out.println("thread B get: " + a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {

            }
        });

        executors.shutdown();

    }
}
