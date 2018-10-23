package com.zlxtk.boot.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 点击菜单时，设置session中的moduleCode属性为相应菜单的module permissionCode
 */
@Slf4j
@Order(100)
@WebFilter(urlPatterns = { "/*" }, filterName = "menuFilter")
public class MenuFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        HttpSession session=req.getSession();
        //TODO 获取访问路径中的permissionCode

        //设置session中的moduleCode属性为相应菜单的module permissionCode


        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
