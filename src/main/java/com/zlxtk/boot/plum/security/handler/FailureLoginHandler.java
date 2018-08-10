package com.zlxtk.boot.plum.security.handler;

import com.zlxtk.boot.plum.base.constants.ApplicationConstants;
import com.zlxtk.boot.plum.base.constants.ErrorCodeConstants;
import com.zlxtk.boot.plum.base.exception.AttempMaxLoginFaildException;
import com.zlxtk.boot.plum.security.exception.AccesssAppDeniedException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
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
        if (exception != null) {
            // 隐藏账户不存在异常，统一抛出认证密码异常
            if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException || exception instanceof InternalAuthenticationServiceException) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                        new InsufficientAuthenticationException(ErrorCodeConstants.LOGIN_ERROR_BAD_CREDENTIALS));
            } else if (exception instanceof AttempMaxLoginFaildException) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                        new InsufficientAuthenticationException(ErrorCodeConstants.LOGIN_ERROR_EXCEED_MAX_TIMES));
            } else if (exception instanceof AccesssAppDeniedException) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                        new InsufficientAuthenticationException(ErrorCodeConstants.LOGIN_APP_UNREGISTER_GROUP));
            } else {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                        new InsufficientAuthenticationException(ErrorCodeConstants.LOGIN_ERROR_BAD_CREDENTIALS));
            }

        }
        response.setStatus(HttpServletResponse.SC_OK);
        request.getRequestDispatcher(ApplicationConstants.LOGIN_ERROR_PAGE).forward(request, response);
    }
}
