package com.repair.agency.controller;

import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.CommandContainer;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import com.repair.agency.controller.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletController", value = "/")
public class ServletController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    CommandContainer commandContainer;

    @Override
    public void init(){
        commandContainer = new CommandContainer();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException  {
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().replaceAll(contextPath, "");
        logger.info("path = " + path);
        Command command = commandContainer.get(path);
        logger.info("command = " + command);
        String forward = command.execute(request, response);
        logger.info( "forward = " + forward);

        if(forward.contains("redirect:")){
            response.sendRedirect(forward.replace("redirect:", contextPath));
        }else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
        ValidationUtil.loggingEnds(this, logger);
    }

}
