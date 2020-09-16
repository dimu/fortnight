package dimu.res.config.redis.distributedlock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.JedisPool;

public class RedisReentrantLock implements DistributedReentrantLock {

    private final ConcurrentMap<Thread, LockData> threadData = new ConcurrentHashMap<>();

    private RedisLockInternals internals;

    private String lockKey;

    public RedisReentrantLock(JedisPool jedisPool, String lockKey) {
        this.lockKey = lockKey;
        this.internals = new RedisLockInternals(jedisPool);
    }

    @Override
    public boolean tryLock(long _maxWaitMillis) throws InterruptedException {
        Thread currentThread = Thread.currentThread();

        LockData lockData = threadData.get(currentThread);
        if (lockData != null) {
            lockData.lockCount.incrementAndGet();
            return true;
        }

        String lockValue = internals.trySettingRedisKey(lockKey, _maxWaitMillis);
        // 锁定成功。
        if (lockValue != null) {
            LockData newLockData = new LockData(currentThread, lockValue);
            threadData.put(currentThread, newLockData);
            return true;
        }

        return false;
    }

    @Override
    public void unlock() {
        Thread currentThread = Thread.currentThread();

        LockData lockData = threadData.get(currentThread);
        if (lockData == null) {
            throw new IllegalMonitorStateException(String.format("not owning the lock %s.", lockKey));
        }

        int newLockCount = lockData.lockCount.decrementAndGet();
        if (newLockCount > 0) {
            return;
        }
        if (newLockCount < 0) {
            throw new IllegalMonitorStateException(String.format("lock count has been negative for lock %s.", lockKey));
        }

        try {
            internals.unsetRedisKey(lockKey, lockData.lockValue);
        } finally {
            threadData.remove(currentThread);
        }

    }

    /**
     * 内部类实现
     * @author dwx
     *
     */
    private static class LockData {

        final Thread ownerThread;

        final String lockValue;

        final AtomicInteger lockCount = new AtomicInteger(1);

        private LockData(Thread owningThread, String lockValue) {
            this.ownerThread = owningThread;
            this.lockValue = lockValue;
        }
    }

}
