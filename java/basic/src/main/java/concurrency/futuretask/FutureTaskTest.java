package concurrency.futuretask;

import java.sql.SQLOutput;
import java.util.concurrent.*;

/**
 * research about futuretask class
 *
 *
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(() ->
        {
//            TimeUnit.SECONDS.sleep(2);
            System.out.println("future task run in: " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });

//        new Thread(task).start();

//        TimeUnit.SECONDS.sleep(1);

        /**
         * call run to start the task
         */
        task.run();

        System.out.println("main thread is: " + Thread.currentThread().getName());



        System.out.println("future task return " + task.get());



//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.shutdown();

    }
}
