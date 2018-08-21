/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.component.distributed.lock;

import java.util.concurrent.TimeUnit;

/**
 * 用途： 分布式锁操作模板
 * 作者: lishuyi
 * 时间: 2018/6/22  15:06
 */
public interface DistributedLockTemplate {
    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback
     * @return
     */
    <T> T lock(DistributedLockCallback<T> callback);

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit
     * @return
     */
    <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit);
}
