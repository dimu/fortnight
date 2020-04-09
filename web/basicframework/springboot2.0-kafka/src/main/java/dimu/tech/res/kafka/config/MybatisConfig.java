package dimu.tech.res.kafka.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis config class，采用
 * @author dwx
 *
 */
@Configuration
@MapperScan("dimu.tech.res.kafka.mapper")
public class MybatisConfig {
    
}
