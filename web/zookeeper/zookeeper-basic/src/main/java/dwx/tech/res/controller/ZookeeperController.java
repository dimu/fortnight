package dwx.tech.res.controller;

import dwx.tech.res.config.ZookeeperConfig;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author dwx
 */
@RestController
@RequestMapping("/zk-basic")
public class ZookeeperController {

    CuratorFramework client = ZookeeperConfig.createClient();

    @PostMapping("/create")
    public void createPath() throws Exception {
        if (null == client.checkExists().forPath("/person") ) {
            client.create().forPath("/person");
        }

        if (null == client.checkExists().forPath("/person/dwx")) {
            client.create().forPath("/person/dwx", "{\"age\":12}".getBytes(StandardCharsets.UTF_8));
        }
    }

    @GetMapping("/get")
    public String getPathValue() throws Exception {
        return new String(client.getData().forPath("/person/dwx"));
    }

    @PutMapping("/update")
    public int updateNodeData() throws Exception {
        int random = SecureRandom.getInstanceStrong().nextInt(100);
        System.out.println(String.format("random value:%d", random));
        client.setData().forPath("/person/dwx", ("{\"age\":" + random + "}").getBytes(StandardCharsets.UTF_8));
        return 1;
    }


//    @PostMapping("/addListener")
//    public void addNodeListener() {
//        client.create().
//    }
}
