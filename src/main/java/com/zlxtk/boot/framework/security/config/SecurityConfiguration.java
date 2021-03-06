package com.zlxtk.boot.framework.security.config;

import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.security.handler.FailureLoginHandler;
import com.zlxtk.boot.framework.security.handler.SuccessLoginHandler;
import com.zlxtk.boot.framework.security.handler.SuccessLogoutHandler;
import com.zlxtk.boot.framework.security.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 17:57
 * <p>
 * 参考
 * https://blog.csdn.net/u012702547/article/details/54319508
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SuccessLoginHandler successLoginHandler;

    @Autowired
    private FailureLoginHandler failureLoginHandler;

    @Autowired
    private SuccessLogoutHandler successLogoutHandler;

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
        /**
         * NoOpPasswordEncoder 明文方式保存
         * BCtPasswordEncoder 强hash方式加密
         * StandardPasswordEncoder SHA-256方式加密
         * 实现PasswordEncoder接口 自定义加密方式
         */
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/error", "/logout").permitAll()  // 都可以访问
                .antMatchers("/h2-console/**").permitAll()  // 都可以访问
                .antMatchers("/", "/index").access("hasRole('USER')")
                .antMatchers("/admin").access("hasRole('ADMIN')")
                .and().formLogin().loginPage("/login").failureUrl(ApplicationConstants.LOGIN_ERROR_PAGE).successHandler(successLoginHandler).failureHandler(failureLoginHandler)
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe().rememberMeParameter("rememberMe").key("plumCKRM").rememberMeServices(rememberMeServices())
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(successLogoutHandler).permitAll()

                .and().csrf().disable()
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//"/css/**", "/js/**", "/html/**", "/font-awesome/**", "/Ionicons/**", "/dist/**", "/bootstrap/**", "/plugins/**", "/**/*.js", "/**/*.css"
        web.ignoring().antMatchers("/font-awesome/**",
                "/html/**",
                "/Ionicons/**",
                "/bootstrap/**",
                "/**/*.css",
                "/**/*.js",
                "/**/*.ttf",
                "/**/*.woff",
                "/**/*.woff2",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.eot",
                "/**/*.svg"
        );

    }

    @Bean
    RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

}
