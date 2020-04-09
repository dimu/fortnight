package dimu.tech.res.kafka.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 自定义线程池,线程池底层是采用{@link java.util.concurrent.LinkedBlockingDeque}实现
 */
@Configuration
public class TaskExecutorPoolConfig {
    
    private final int corePoolSize = 8;
    
    private final int maxPoolSize = 100;
    
    private final int queueCapacity = 10000;
    
    private final String threadNamePrefix = "dimuTaskExecutor-";

    /**
     * config pool task executor bean 
     * @return thread pool instance
     */
    @Bean
    public Executor poolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        return executor;
    }
}
