
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
 *
 * session中的数据：
 * SPRING_SECURITY_CONTEXT-------org.springframework.security.core.context.SecurityContextImpl@b4418cf8: Authentication: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@b4418cf8: Principal: SysUser(id=1, username=admin, truename=管理员, nickname=管理员, password=123456, email=null, sex=null, phone=null, roles=[SysRole(id=1, roleCode=ROLE_ADMIN, roleName=管理员, users=null, permissions=null), SysRole(id=2, roleCode=ROLE_USER, roleName=用户, users=null, permissions=null)]); Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@ffff4c9c: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: 138492aa-3c91-45b0-9eac-326286ed5c57; Granted Authorities: ROLE_USER, ROLE_ADMIN
 * SPRING_SECURITY_SAVED_REQUEST-------DefaultSavedRequest[http://localhost:8080/plum/]
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
