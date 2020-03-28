package connectionpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * connection pool test ,to see the performance enhance
 *
 * @author dwx
 */
public class ConnectionPoolTest {

    public static int thread_size = 100;

    static CountDownLatch start = new CountDownLatch(1);

    /**
     * the pool size
     */
    static CountDownLatch end = new CountDownLatch(thread_size);

    public static ConnectionPool connectionPool = new ConnectionPool(10);

    public static void main(String[] args) throws InterruptedException {

        /**
         * the initial number of connection pool : 10
         */
        int repeatCount = 20;
        AtomicInteger getCount = new AtomicInteger();
        AtomicInteger notGetCount = new AtomicInteger();

        for (int i = 0; i < thread_size; i++) {
            Thread thread = new Thread(new ConnectionClient(repeatCount, getCount, notGetCount));
            thread.start();
        }

        start.countDown();
        end.await();

        System.out.println("total invoke " + (repeatCount * thread_size));
        System.out.println("get connection: " + getCount);
        System.out.println("not get connection: " + notGetCount);

    }

    static class ConnectionClient implements Runnable {

        private int repeatTime;

        private AtomicInteger getCount;

        private AtomicInteger notGetCount;

        public ConnectionClient(int repeatTime, AtomicInteger getCount, AtomicInteger notGetCount) {
            this.repeatTime = repeatTime;
            this.getCount = getCount;
            this.notGetCount = notGetCount;
        }

        @Override
        public void run() {

            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (repeatTime > 0) {
                try {
                    Connection connection = connectionPool.fetchConnection(1000);
                    if (null != connection) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            connectionPool.releaseConnection(connection);
                            getCount.incrementAndGet();
                        }
                    } else {
                        notGetCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    repeatTime--;
                }
            }
            end.countDown();

        }
    }
}
