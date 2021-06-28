package com.repair.agency.controller.filters;

import com.repair.agency.controller.command.ErrorPageCommand;
import com.repair.agency.controller.command.LoginPageCommand;
import com.repair.agency.controller.command.manager.ManagerReceiptListCommand;
import com.repair.agency.controller.command.master.MasterReceiptListCommand;
import com.repair.agency.controller.command.user.UserReceiptListCommand;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(EncodingFilter.class.getName());
    HttpServletRequest httpReq;
    HttpServletResponse httpResp;


/*    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }*/

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
            String loginPath = httpReq.getContextPath() + forward/*.replace(".jsp", "")*/;
            httpReq.setAttribute("action", loginPath);
            httpReq.getRequestDispatcher(forward).forward(httpReq,httpResp);
            return;
        }

        /*if (role != null && path.contains("/login")) {
            httpReq.setAttribute("errorMessage", "AlreadyLoggedIN");
            Optional<String> forward = Optional.empty();// = new ErrorPageCommand().execute(httpReq, httpResp);
            *//*if ("MANAGER".equals(role)) {
                forward = Optional.of(new ManagerReceiptListCommand(new MasterService(), new ManagerService()).execute(httpReq, httpResp));
            }
            if ("MASTER".equals(role)){
                forward = Optional.of(new MasterReceiptListCommand(new MasterService()).execute(httpReq, httpResp));
            }
            if ("USER".equals(role)){
                forward = Optional.of(new UserReceiptListCommand(new UserService()).execute(httpReq, httpResp));
            }*//*
            httpReq.getRequestDispatcher(forward.orElse(new ErrorPageCommand().execute(httpReq, httpResp))).forward(request, response);
            chain.doFilter(request, response);
            return;
        }
        if ( "MANAGER".equals(role) && (!path.contains("/manager") && (path.contains("/user") || path.contains("/master")) *//*&& !"/repair/".equals(path)*//*)) {
            httpReq.setAttribute("errorMessage", "HasNoRights");
            //String forward = new ManagerReceiptListCommand(new MasterService(), new ManagerService()).execute(httpReq, httpResp);
            //httpReq.getRequestDispatcher(forward).forward(request, response);
            String forward = new ErrorPageCommand().execute(httpReq, httpResp);
            httpReq.getRequestDispatcher(forward).forward(request, response);

            chain.doFilter(request, response);  //todo is it correct?
            return; //todo is it correct?
        }*/
/*
        if ("MASTER".equals(role) && (!path.contains("/master") && !"/repair/".equals(path))) {
            httpReq.setAttribute("errorMessage", "HasNoRights");
            String forward = new MasterReceiptListCommand(new MasterService()).execute(httpReq, httpResp);
            httpReq.getRequestDispatcher(forward).forward(request, response);
            chain.doFilter(request, response);  //todo is it correct?
            return;
        }
        if ("USER".equals(role) && (!path.contains("/user") && !"/repair/".equals(path))) {
            httpReq.setAttribute("errorMessage", "HasNoRights");
            String forward = new UserReceiptListCommand(new UserService()).execute(httpReq, httpResp);
            httpReq.getRequestDispatcher(forward).forward(request, response);
            chain.doFilter(request, response);  //todo is it correct?
            return;
        }*/

        logger.info(this.getClass().getSimpleName() + "--> ends");

        chain.doFilter(request, response);
    }
}
