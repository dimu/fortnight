package concurrency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CycleSleepingTask implements Runnable {

    private int countDown = 5;

    @Override
    public void run() {
        try {
            while (countDown-- > 0) {
                System.out.println(Thread.currentThread().getName() + countDown + new Date());
                TimeUnit.MILLISECONDS.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new CycleSleepingTask());
        }
        exec.shutdown();
        List<String> testArray = new ArrayList<String>(10);
        testArray.add(0, "0");
        testArray.add(1, "1");
        testArray.add(2, "1");
        testArray.add(3, "1");
        testArray.add(4, "1");
        System.out.println(testArray.size());
        testArray.parallelStream().forEach(item -> {
            sendCommand();
        });
    }
    
    public static void sendCommand() {
        int count  = 5;
        while (count-- > 0) {
            System.out.println(Thread.currentThread().getName() + count + new Date());
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
