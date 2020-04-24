package dwx.tech.res.zookeeper;

import dwx.tech.res.zookeeper.watch.CustomWatcher;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * zookeeper basic use
 *
 * @author dwx
 */
@SpringBootApplication
public class ZookeeperApplication implements CommandLineRunner {

    @Resource
    private CuratorFramework curatorFramework;

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//
//        String path = "/dwx/age";
//
//        Watcher watcher = new CustomWatcher();
//
//        curatorFramework.start();
//
//        curatorFramework.getData().usingWatcher(watcher).forPath(path);
////        curatorFramework.getChildren().usingWatcher(watcher).forPath("/dwx");
//
//        curatorFramework.setData().forPath(path, "efd".getBytes());
//
////        curatorFramework.create().forPath("/dwx/name4", "cdf".getBytes());

    }
}
