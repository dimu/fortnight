package dwx.tech.res.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLockTest {

    @Resource
    private CuratorFramework curatorFramework;

    /**
     * how to use distibuted lock
     * @throws Exception
     */
    @Test
    public void distributedLockTest() throws Exception {
        String path = "/dwx/age";
        curatorFramework.start();
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        if (lock.acquire(10, TimeUnit.SECONDS)) {
            try {
                System.out.println("acquire lock:  " + curatorFramework.getZookeeperClient().toString());
            } finally {
                lock.release();
            }
        } else {
            System.out.println("lock 10 seconds timeout!");
        }
        curatorFramework.close();
    }
}
