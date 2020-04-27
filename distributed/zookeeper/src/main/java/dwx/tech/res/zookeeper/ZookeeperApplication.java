package dwx.tech.res.zookeeper;

import dwx.tech.res.zookeeper.watch.CustomWatcher;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
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

        String path = "/dwx/name";

        curatorFramework.start();

        Stat stat =curatorFramework.checkExists().forPath(path);


        if ( null != stat ) {
            System.out.println(Thread.currentThread().getName() + ": node already exist: " + stat.toString());
            for (long count = 0; count <= (long)0x1 << 32; count ++) {
                System.out.println(Thread.currentThread().getName() + ": current count value:" + count);
                if (!curatorFramework.getZookeeperClient().isConnected()) {
                    curatorFramework.getZookeeperClient().start();
                } else {
                    System.out.println("client is connected!");
                }
                curatorFramework.setData().forPath(path, String.valueOf(123).getBytes());
            }
        } else {
            String age = curatorFramework.create().forPath(path, String.valueOf(30).getBytes());
            System.out.println("set value: " + age);
        }

//        TimeUnit.MINUTES.sleep(10);
//        curatorFramework.close();

    }
}
