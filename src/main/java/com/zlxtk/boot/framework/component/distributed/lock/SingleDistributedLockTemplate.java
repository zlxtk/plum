/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.component.distributed.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 用途：Single Instance mode 分布式锁模板
 * 作者: lishuyi
 * 时间: 2018/6/22  15:07
 */
public class SingleDistributedLockTemplate implements DistributedLockTemplate {

    private RedissonClient redisson;

    public SingleDistributedLockTemplate() {
    }

    public SingleDistributedLockTemplate(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @Override
    public <T> T lock(DistributedLockCallback<T> callback) {
        RLock lock = null;
        try {
            lock = redisson.getLock(callback.getLockName());
            lock.lock();
            return callback.process();
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    @Override
    public <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit) {
        RLock lock = null;
        try {
            lock = redisson.getLock(callback.getLockName());
            lock.lock(leaseTime, timeUnit);
            return callback.process();
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    public void setRedisson(RedissonClient redisson) {
        this.redisson = redisson;
    }
}
