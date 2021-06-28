package com.repair.agency.controller.command;

import com.repair.agency.controller.command.manager.EditReceiptCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession(false);

        if (session != null) {
            HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
            logger.info("LOGGED USERS = " + loggedUsers);
            String login = (String)session.getAttribute("login");
            logger.info("USER LOGIN = " + login);
            loggedUsers.remove(login);
            session.invalidate();
        }

        logger.info(this.getClass().getSimpleName() + " --> ends");
        return "redirect:";
    }
}
