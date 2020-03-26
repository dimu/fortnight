package connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * write a simple database connection pool
 *
 * @author dwx
 */
public class ConnectionPool {

    /**
     * use LinkedList as a connection pool storage
     */
    private LinkedList<Connection> pool = new LinkedList<>();

    /**
     * constructor method
     * @param initialSize pool size
     */
    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for(int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * fetch connection
     * @param millis timeout
     * @return null if not get a connection, otherwise return the connection
     * @throws InterruptedException
     */
    public Connection fetchConnection(long millis) throws InterruptedException {
        synchronized (pool) {
            if (millis <= 0) {
                while(pool.isEmpty()) {
                    /**
                     * release the pool lock，自旋
                     */
                    pool.wait();
                }

                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + millis;
                long remains = millis;

                /**
                 * 超时等待与自旋
                 */
                while(pool.isEmpty() && remains > 0) {
                    pool.wait(remains);
                    remains = future - System.currentTimeMillis();
                }

                Connection connection = null;

                if (!pool.isEmpty()) {
                    connection = pool.removeFirst();
                }

                return  connection;
            }
        }
    }
}
