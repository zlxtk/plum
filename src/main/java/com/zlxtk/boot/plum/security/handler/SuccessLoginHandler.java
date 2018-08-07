
package com.zlxtk.boot.plum.security.handler;

import com.zlxtk.boot.plum.base.constants.ApplicationConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用途：登录成功后的操作，不同角色跳转不同首页可以在这里完成
 */
@Slf4j
@NoArgsConstructor
@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {


    @Getter
    @Setter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        response.setStatus(HttpServletResponse.SC_OK);
        request.getRequestDispatcher(targetUrl).forward(request, response);
    }

    /**
     * 不同角色跳不同页面的处理
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = ApplicationConstants.HOME_PAGE;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (roles.contains("ROLE_USER")) {
            url = "/index";
        }

        if (roles.contains("ROLE_ADMIN")) {
            url = "/admin";
        }

        return url;
    }

}
