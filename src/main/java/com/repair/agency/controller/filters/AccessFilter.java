package com.repair.agency.controller.filters;

import com.repair.agency.controller.command.LoginPageCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(EncodingFilter.class.getName());
    HttpServletRequest httpReq;
    HttpServletResponse httpResp;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info(this.getClass().getSimpleName() + "starts");
        httpReq = (HttpServletRequest) request;
        httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession();
        String path = httpReq.getRequestURI();
        String role = (String) session.getAttribute("role");
        if (role == null && (path.contains("/manager") || path.contains("/user") || path.contains("/master") || path.contains("/logout"))) {
            httpReq.setAttribute("access", "NotLoggedIn");
            String forward = new LoginPageCommand().execute(httpReq, httpResp);
            logger.info("Forward in Access filter = " + forward);
            String loginPath = httpReq.getContextPath() + forward;
            httpReq.setAttribute("action", loginPath);
            httpReq.getRequestDispatcher(forward).forward(httpReq,httpResp);
            return;
        }
        logger.info(this.getClass().getSimpleName() + "--> ends");
        chain.doFilter(request, response);
    }
}
