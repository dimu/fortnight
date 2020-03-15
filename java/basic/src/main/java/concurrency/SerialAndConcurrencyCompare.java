package concurrency;

/**
 * 串行与并行效率对比,两个线程，一个执行累加，一个执行累减
 * 由于cpu计算频率很高，只有到10亿级别才能明显看出并发比串行性能
 * 高
 * @author dwx
 */
public class SerialAndConcurrencyCompare {

    public static final int count = 1000000000;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * concurrency method
     * @throws InterruptedException 线程唤醒异常
     */
    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread addThread = new Thread(()->{
            int a = 0;
            for (long i = 0; i< count; i++){
                a += 5;
            }
        });
        addThread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        addThread.join();
        long end = System.currentTimeMillis();
        System.out.println("concurrency cost:" + (end - start));
    }

    /**
     * 串行接口
     */
    private static void serial()  {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i< count; i++){
            a += 5;
        }

        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }

        long end = System.currentTimeMillis();
        System.out.println("serial cost:" + (end - start));
    }
}
