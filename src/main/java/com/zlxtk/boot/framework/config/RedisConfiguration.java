package com.zlxtk.boot.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Redis 配置信息
 *
 */
@Configuration
@EnableCaching
@EnableRedisHttpSession
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {
}
