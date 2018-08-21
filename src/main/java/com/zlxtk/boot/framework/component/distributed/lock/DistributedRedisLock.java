/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.component.distributed.lock;

import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.config.AppConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用途：
 * 作者: lishuyi
 * 时间: 2018/6/22  17:40
 */
@Component
public class DistributedRedisLock {
    
    @Autowired
    private RedissonClient redisson;

    @Autowired
    private AppConfig config;

    private static String redisKeyPrefix;

    private static DistributedRedisLock lockUtils;

    @PostConstruct
    public void init() {
        lockUtils = this;
        lockUtils.redisson = this.redisson;
        lockUtils.redisKeyPrefix = config.getRedisKeyPrefix();
    }

    /**
     * 获取锁
     * @param lockName
     */
    public static void tryLock(String lockName){
        String key = redisKeyPrefix + lockName;
        RLock mylock = lockUtils.redisson.getLock(key);
        mylock.lock(ApplicationConstants.REDIS_LOCK_DEFAULT_TIMEOUT, ApplicationConstants.REDIS_LOCK_DEFAULT_TIME_UNIT); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
    }

    /**
     * 释放锁
     * @param lockName
     */
    public static void unlock(String lockName){
        String key = redisKeyPrefix + lockName;
        RLock mylock = lockUtils.redisson.getLock(key);
        mylock.unlock();
    }

    /**
     * 获取锁
     * @param lockName
     */
    public static void tryLock(String lockName, int seconds){
        String key = redisKeyPrefix + lockName;
        RLock mylock = lockUtils.redisson.getLock(key);
        mylock.lock(seconds, ApplicationConstants.REDIS_LOCK_DEFAULT_TIME_UNIT); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
    }

}
