package dimu.ssm.config.redis.distributedlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;

/**
 * redis lock template 
 * @author dwx
 *
 * @param <T>
 */
public class RedisLockTemplate<T> implements DistributedLockTemplate<T> {
    
    public static final Logger LOG = LoggerFactory.getLogger(RedisLockTemplate.class);

    private JedisPool jedisPool;

    public RedisLockTemplate(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public T execute(String _lockKey, long _maxWaitMillis, DistributedLockCallback<T> _callback) {
        RedisReentrantLock lock = null;
        boolean gotLock = false;

        try {
            lock = new RedisReentrantLock(jedisPool, _lockKey);

            if (lock.tryLock(_maxWaitMillis)) {
                gotLock = true;
                return _callback.onLockingSucceeded();
            } else {
                return _callback.onLockingFailed();
            }

        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (gotLock) {
                lock.unlock();
            }
        }
        return null;
    }

}
