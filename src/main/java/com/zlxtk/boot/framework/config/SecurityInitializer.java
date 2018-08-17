
package com.zlxtk.boot.framework.config;

import com.zlxtk.boot.framework.security.config.SecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 用途：Spring Session with Redis
 * 参考：
 *      https://docs.spring.io/spring-session/docs/current/reference/html5/guides/java-security.html
 *      https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html
 *      http://blog.didispace.com/spring-session-xjf-1/
 *      http://blog.didispace.com/spring-session-xjf-2/
 *      http://blog.didispace.com/spring-session-xjf-3/
 * 作者: lishuyi
 * 时间: 2018/3/7  14:39
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer { // <1>

    public SecurityInitializer() {
        super(SecurityConfiguration.class);
    }
}
