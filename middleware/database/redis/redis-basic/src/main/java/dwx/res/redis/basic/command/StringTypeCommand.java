package dwx.res.redis.basic.command;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * redis string type command
 * @author dwx
 */
@Configuration
public class StringTypeCommand {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


}
