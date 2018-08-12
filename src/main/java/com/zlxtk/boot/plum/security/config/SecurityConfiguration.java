package com.zlxtk.boot.plum.security.config;

import com.zlxtk.boot.plum.base.constants.ApplicationConstants;
import com.zlxtk.boot.plum.security.handler.FailureLoginHandler;
import com.zlxtk.boot.plum.security.handler.SuccessLoginHandler;
import com.zlxtk.boot.plum.security.handler.SuccessLogoutHandler;
import com.zlxtk.boot.plum.security.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

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
     * @return 封装身份认证提供者
     */
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userService);  //自定义的用户和角色数据提供者
////        authenticationProvider.setPasswordEncoder(passwordEncoder()); //设置密码加密对象
//        return authenticationProvider;
//    }

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
//                .antMatchers("/css/**", "/js/**", "/html/**", "/font-awesome/**", "/Ionicons/**", "/dist/**", "/bootstrap/**", "/plugins/**").permitAll()
                .antMatchers("/error", "/logout").permitAll()  // 都可以访问
                .antMatchers("/h2-console/**").permitAll()  // 都可以访问
                .antMatchers("/", "/index").access("hasRole('USER')")
                .antMatchers( "/admin").access("hasRole('ADMIN')")
                .and().formLogin().loginPage("/login").failureUrl(ApplicationConstants.LOGIN_ERROR_PAGE).successHandler(successLoginHandler).failureHandler(failureLoginHandler)
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe().rememberMeParameter("rememberMe").tokenValiditySeconds(60 * 60 * 24 * 7).key("plumCKRM")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(successLogoutHandler).permitAll()
                .and().csrf().disable();
        ;

//        http
//                .addFilterBefore(captchaUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(uumsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(rsaAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(ssoAuthenticationFilter(), UumsAuthenticationFilter.class)
//                .authorizeRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .antMatchers("/error", "/login", "/logout").permitAll()  // 都可以访问
//                .antMatchers("/h2-console/**", "/html/**").permitAll()  // 都可以访问
//                .antMatchers("/httpauth/**", "/action/anonymous/**", "/anonymous/**", "/services/**").permitAll()  // 都可以访问
//                .antMatchers("/action/**").hasRole("USER")   // 需要相应的角色才能访问
//                .antMatchers("/sys/admin/**").hasAnyRole("ADMIN", "SUPERVISOR")   // 需要相应的角色才能访问
//                .anyRequest().authenticated()
//                .and().formLogin().successHandler(successLoginHandler) // 成功登入后，重定向到首页
//                .loginPage(ApplicationConstants.LOGIN_PAGE).failureUrl(ApplicationConstants.LOGIN_ERROR_PAGE) // 自定义登录界面
//                .failureHandler(failedLoginHandler) //记录登录错误日志，并自定义登录错误提示信息
//                .and().logout().logoutSuccessHandler(successLogoutHandler) // 成功登出后，重定向到登陆页
//                .and().exceptionHandling().accessDeniedPage("/403")// 处理异常，拒绝访问就重定向到 403 页面
//                .and().headers().frameOptions().sameOrigin()
//                .and().csrf().disable()
////                .and().csrf().requireCsrfProtectionMatcher(swagger2CsrfProtection)
////                .and()
////                .sessionManagement().invalidSessionUrl(ApplicationConstants.LOGIN_PAGE).maximumSessions(1)
////                .sessionRegistry(sessionRegistry).expiredUrl(ApplicationConstants.LOGIN_PAGE);
//                .sessionManagement().maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry());
//        //.expiredUrl(ApplicationConstants.LOGIN_PAGE);
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
}
