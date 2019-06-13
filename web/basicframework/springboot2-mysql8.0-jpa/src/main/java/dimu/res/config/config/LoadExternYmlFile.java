package dimu.res.config.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * 初始化如何加载非application等文件到内存中
 * @auth dimu
 */
@Configuration
public class LoadExternYmlFile {

    @Resource
    public void getDefaultYmlProperties(Environment environment) {

        String port = environment.getProperty("server.port");
        System.out.println("server port in application: " + port);
    }
}