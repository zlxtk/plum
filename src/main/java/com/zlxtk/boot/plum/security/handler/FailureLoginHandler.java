package com.zlxtk.boot.plum.security.handler;

import com.zlxtk.boot.plum.base.constants.ApplicationConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:登录失败后的处理
 * @Auther: tangyake
 * @Date: 2018/8/7 15:53
 */
@Slf4j
@NoArgsConstructor
@Component
public class FailureLoginHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        request.getRequestDispatcher(ApplicationConstants.LOGIN_ERROR_PAGE).forward(request, response);
    }
}
