package dwx.tech.res.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zookeeper env config
 *
 * @author dwx
 * @date 2022-01-17
 */
public class ZookeeperConfig {

//    public static final String CONNECT_STRING = "172.19.3.151:2181;172.19.3.152:2181;172.19.3.153:2181";
    public static final String CONNECT_STRING = "172.19.3.151:2181";

    private static CuratorFramework client;

    public static CuratorFramework createClient() {
        if (null == client) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client =  CuratorFrameworkFactory.newClient(CONNECT_STRING,retryPolicy);
            client.start();
        }

        return client;
    }
}
