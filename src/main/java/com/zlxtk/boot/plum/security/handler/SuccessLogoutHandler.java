
package com.zlxtk.boot.plum.security.handler;

import com.zlxtk.boot.plum.base.constants.ApplicationConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用途：记录登出日志
 * 作者: lishuyi
 * 时间: 2018/2/25  18:49
 */
@Slf4j
@NoArgsConstructor
@Component
public class SuccessLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.debug("Logout Sucessfull with Principal: " + authentication);
        response.setStatus(HttpServletResponse.SC_OK);
        request.getRequestDispatcher(ApplicationConstants.LOGIN_PAGE).forward(request, response);
    }
}
