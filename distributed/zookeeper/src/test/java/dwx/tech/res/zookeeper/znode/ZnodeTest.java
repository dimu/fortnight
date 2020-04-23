package dwx.tech.res.zookeeper.znode;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZnodeTest {

    @Resource
    private CuratorFramework curatorFramework;

    @Test
    public void createPersistentNode() throws Exception {
        curatorFramework.start();

        Stat stat =curatorFramework.checkExists().forPath("/dwx2/age");

        if ( null != stat ) {
            System.out.println("node already exist: " + stat.toString());
        } else {
            String age = curatorFramework.create().forPath("/dwx2/age", String.valueOf(30).getBytes());
            System.out.println("set value: " + age);
        }
        curatorFramework.close();
    }

    @Test
    public void createTTLPersistentNode() throws Exception {
        curatorFramework.start();

        Stat stat =curatorFramework.checkExists().forPath("/dwx2/age");

        if ( null != stat ) {
            System.out.println("node already exist: " + stat.toString());
        } else {
            String age = curatorFramework.create().forPath("/dwx2/age", String.valueOf(30).getBytes());
            System.out.println("set value: " + age);
        }
        curatorFramework.close();
    }
}
