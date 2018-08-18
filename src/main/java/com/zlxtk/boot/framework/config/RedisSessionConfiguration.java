
package com.zlxtk.boot.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 用途：Spring Session with Redis
 * 参考：https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html
 *      http://blog.didispace.com/spring-session-xjf-2/
 * 作者: lishuyi
 * 时间: 2018/3/7  14:39
 *
 */
@Slf4j
@Configuration
@EnableRedisHttpSession
@EnableCaching
public class RedisSessionConfiguration {

    @Autowired
    private AppConfig config;

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SECURITYID"); // <1>
        //如果application.properties设置spring.session.cookie.path，以设置为准，通常为/，不设置则默认为应用context
        if(StringUtils.isNotEmpty(config.getCookiePath()))
            serializer.setCookiePath(config.getCookiePath()); // <2>
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
        return serializer;
    }

}
