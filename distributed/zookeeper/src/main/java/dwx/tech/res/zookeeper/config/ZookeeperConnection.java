package dwx.tech.res.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zookeeper connection config
 *
 * @Date 2020-04-23
 * @author dwx
 */
@Configuration
public class ZookeeperConnection {

    @Value("${zk.servers}")
    public String servers;

    /**
     * 返回zk framework-style client
     * @return 生成的单例
     */
    @Bean
    public CuratorFramework curatorFramework() {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(servers, new RetryNTimes(3, 10));

        return curatorFramework;
    }

}
