package connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * connection pool test ,to see the performance enhance
 *
 * @author dwx
 */
public class ConnectionPoolTest {

    static CountDownLatch start = new CountDownLatch(1);

    /**
     * the pool size
     */
    static CountDownLatch end = new CountDownLatch(10);

    public static void main(String[] args) {

        /**
         * the initial number of connection pool : 10
         */
        ConnectionPool connectionPool = new ConnectionPool(10);


    }

    class ConnectionClient implements Runnable {

        ConnectionPool connectionPool;

        public ConnectionClient(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Connection connection = connectionPool.fetchConnection();
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
