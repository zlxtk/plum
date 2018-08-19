
package com.zlxtk.boot.framework.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 用途：Spring Session with Redis
 * 参考：https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html
 * http://blog.didispace.com/spring-session-xjf-2/
 * 作者: lishuyi
 * 时间: 2018/3/7  14:39
 */
@Slf4j
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SECURITYID"); // <1>
        // 目前设计不能设置，否则导致不同应用相同Cookie在检查Session到期时间时报错
        // Failed to deserialize object type; nested exception is java.lang.ClassNotFoundException: com.simbest.boot.uums.role.model.SysRole
//        if(StringUtils.isNotEmpty(config.getCookiePath()))
//            serializer.setCookiePath(config.getCookiePath()); // <2>
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
        return serializer;
    }

}
