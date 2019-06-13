package dimu.res.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"classpath:externproperties.yml","classpath:application.yml","classpath:map.properties"})
public class LoadExternYmlFileTest {

    @Resource
    private Environment environment;

    @Test
    public void loadEnvironment() {
        String port = environment.getProperty("server.port");
        System.out.println("server port : " + port);

        PropertySource source = ((StandardEnvironment) environment).getPropertySources()
                .get("class path resource [externproperties.yml]");
        Map<String, String> appMap = (Map<String, String>) source.getSource();
        System.out.println(appMap.get("product.appid"));
    }
}
