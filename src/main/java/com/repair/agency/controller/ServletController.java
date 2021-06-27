package com.repair.agency.controller;

import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.CommandContainer;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletController", value = "/")
public class ServletController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());


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
        String path = request.getRequestURI().replaceAll("/repair", "");
        logger.info("path = " + path);
        Command command = CommandContainer.get(path);
        String forward = command.execute(request, response);

        logger.info( "forward = " + forward);

        if(forward.contains("redirect:")){
            logger.info("IN REDIRECT," + forward.replace("redirect:", "/repair"));
            System.out.println("getContextPath = " + request.getContextPath());
            response.sendRedirect(forward.replace("redirect:", "/repair"));
        }else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }

}
