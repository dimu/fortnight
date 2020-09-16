package dimu.res.config.redis.distributedlock;

public interface DistributedLockTemplate<T> {

    /**
     * @param _lockKey       锁的唯一标识
     * @param _maxWaitMillis 获取排它锁时的最大等待时间（单位：毫秒）
     * @param _callback  获取锁成功或者失败的回调函数
     * @return 回调方法中返回的数据类型
     */
    T execute(String _lockKey, long _maxWaitMillis, DistributedLockCallback<T> _callback);

}
