/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.component.distributed.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 用途： 创建分布式锁模板实例的工厂Bean
 * 作者: lishuyi
 * 时间: 2018/6/22  15:09
 */
@Slf4j
public class DistributedLockFactoryBean implements FactoryBean<DistributedLockTemplate> {

    private LockInstanceMode mode;

    private DistributedLockTemplate distributedLockTemplate;

    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.debug("--------------------------------------------RedissonClient start");
    }

    @PreDestroy
    public void destroy() {
        //redisson.shutdown(); //交由RedisConfiguration.redissonClient()进行shutdown
        log.debug("--------------------------------------------RedissonClient shutdown");
    }

    @Override
    public DistributedLockTemplate getObject() throws Exception {
        switch (mode) {
            case SINGLE:
                distributedLockTemplate = new SingleDistributedLockTemplate(redisson);
                break;
        }
        return distributedLockTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return DistributedLockTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMode(String mode) {
        if (mode==null||mode.length()<=0||mode.equals("")) {
            throw new IllegalArgumentException("未找到redisson.mode配置项");
        }
        this.mode = LockInstanceMode.parse(mode);
        if (this.mode == null) {
            throw new IllegalArgumentException("不支持的分布式锁模式");
        }
    }

    private enum LockInstanceMode {
        SINGLE;
        public static LockInstanceMode parse(String name) {
            for (LockInstanceMode modeIns : LockInstanceMode.values()) {
                if (modeIns.name().equals(name.toUpperCase())) {
                    return modeIns;
                }
            }
            return null;
        }
    }
}
