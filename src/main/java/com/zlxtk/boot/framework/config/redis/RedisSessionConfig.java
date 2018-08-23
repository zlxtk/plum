
package com.zlxtk.boot.framework.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 用途：spring-data-redis
 * 参考：https://docs.spring.io/spring-data/redis/docs/2.0.1.RELEASE/reference/html/#new-in-2.0.0
 * 作者: zlxtk
 * 时间: 2018/8/18
 */
@Slf4j
@Configuration
public class RedisSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SECURITYID"); // <1>
        // 目前设计不能设置，否则导致不同应用相同Cookie在检查Session到期时间时报错
        // Failed to deserialize object type; nested exception is java.lang.ClassNotFoundException: com.simbest.boot.uums.role.model.SysRole
//        if(StringUtils.isNotEmpty(config.getCookiePath()))
//            serializer.setCookiePath(config.getCookiePath()); // <2>
        //这个正则可能表达不了域名
//        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
        serializer.setDomainNamePattern("/");
        return serializer;
    }

}
