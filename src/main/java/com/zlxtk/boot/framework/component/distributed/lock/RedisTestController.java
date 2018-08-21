/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.component.distributed.lock;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用途：
 * 作者: lishuyi
 * 时间: 2018/6/22  20:30
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Resource
    private DistributedLockTemplate distributedLockTemplate;

    @PostMapping(value = "/lock")
    public void lock() {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                try {
                    String key = "test123";
                    DistributedRedisLock.tryLock(key);
                    Thread.sleep(1000 * 10); //获得锁之后可以进行相应的处理
                    System.err.println("======获得锁后进行相应的操作======");
                    DistributedRedisLock.unlock(key);
                    System.err.println("=============================");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

    @PostMapping(value = "/template")
    public void template() {
        for (int i = 0; i < 100; i++) {
            DisplayMessage m = new DisplayMessage(distributedLockTemplate);
            m.start();
        }
    }

    public class DisplayMessage extends Thread {
        DistributedLockTemplate distributedLockTemplate ;
        public DisplayMessage(DistributedLockTemplate distributedLockTemplate){
            this.distributedLockTemplate = distributedLockTemplate;
        }

        @Override
        public void run() {
            distributedLockTemplate.lock(new DistributedLockCallback<Object>() {
                @Override
                public Object process() {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "-" + "get lock");
                    try {
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public String getLockName() {
                    return "MyLock";
                }
            });
        }
    }


}
