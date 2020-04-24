package dwx.tech.res.zookeeper.watch;

import org.apache.curator.framework.CuratorFramework;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomWatherTest {

    @Resource
    private CuratorFramework curatorFramework;

    public void nodeWatcherTest() {
//        curatorFramework.getCuratorListenable().addListener(new CustomWatcher());
    }
}
