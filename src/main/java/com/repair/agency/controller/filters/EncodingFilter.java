package com.repair.agency.controller.filters;

import com.repair.agency.controller.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(EncodingFilter.class.getName());
    public static final String EN = "en";
    public static final String LANG = "lang";

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info(this.getClass().getSimpleName() + " --> starts");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String lang = EN;
        if (session.getAttribute(LANG) != null) {
            lang = (String) session.getAttribute(LANG);
        }
        if (request.getParameter(LANG) != null) {
            lang = request.getParameter(LANG);
        }
        session.setAttribute(LANG, lang);

        logger.info(LANG + " is set " + lang);
        ValidationUtil.loggingEnds(this, logger);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
