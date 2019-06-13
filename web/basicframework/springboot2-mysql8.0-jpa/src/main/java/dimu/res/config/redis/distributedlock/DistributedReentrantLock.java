package dimu.ssm.config.redis.distributedlock;

/**
 * 分布式可重入锁接口
 * @author dwx
 *
 */
public interface DistributedReentrantLock {

    /**
     * 尝试锁，如果在最大等待时间都没能获取锁就返回失败
     * @param maxWaitMillis 最大等待时间
     * @return true 获取锁成功， false 获取锁失败
     * @throws InterruptedException 
     */
    boolean tryLock(long maxWaitMillis) throws InterruptedException;
    
    /**
     * 释放锁
     */
    void unlock();
}
