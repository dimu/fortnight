package dwx.res.redis.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * redis pipeline research
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PipelineTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void pipelineTest() {
        redisTemplate.executePipelined(Connect)
        redisTemplate.executePipelined()
    }
}
