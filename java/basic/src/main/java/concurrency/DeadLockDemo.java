package concurrency;

/**
 * use java VisualVM can see the dead lock output
 * @author dwx
 */
public class DeadLockDemo {

    public static final String A = "a";

    public static final String B = "b";

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    /**
                     * force thread sleep
                     */
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * race condition
                 */
                synchronized (B) {
                    System.out.println("try to lock b");
                }

            }
        });
        t1.setName("thread1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.println("try to lock a");
                }

            }
        });
        t2.setName("thread2");

        t1.start();
        t2.start();

    }
}
