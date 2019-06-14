package dimu.ssm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis config class，采用
 * @author dwx
 *
 */
@Configuration
@MapperScan("dimu.res.mapper")
public class MybatisConfig {
    
}
