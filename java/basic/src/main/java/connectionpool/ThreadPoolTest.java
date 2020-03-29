package connectionpool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool<Job> threadPool =  new DefaultThreadPool<>();
        threadPool.execute(new Job());
        TimeUnit.SECONDS.sleep(10);
        threadPool.removeWorker(1);
        threadPool.execute(new Job());
        threadPool.removeWorker(1);
    }

    static class Job  implements  Runnable {

        @Override
        public void run() {
            System.out.println("Thread execute: " + Thread.currentThread().getName());
        }
    }
}
