package com.repair.agency.controller.command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.repair.agency.controller.command.manager.EditReceiptCommand;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

public class Util {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    public static final String USER = "user";
    public static final String SESSION_ID = "_sessionId";
    private static final String LOGIN = "login";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String LOGGED_SUCCESSFULLY_WITH_ROLE = " logged successfully with role ";
    public static final String ENDS = " --> ends";

    public static void processInvalidCredentials(HttpServletRequest request, String message) {
        logger.info( " Util processInvalidCredentials --> done with message: " + message);
        request.setAttribute("errorMessage", message);
    }

    public static void addUserIntoSessionAndContext(HttpServletRequest request, Logger logger, User user) {
        //request.getServletContext().setAttribute("userLogin", user.getLogin());

        //ServletContext context = request.getSession().getServletContext();
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext() // todo Optional
                .getAttribute("loggedUsers");; // todo getSession().getServletContext() on session or on request ???

        boolean isFirstUser = false;
        if(loggedUsers == null){

            loggedUsers = new HashSet<>();
            isFirstUser = true;
        }
        loggedUsers.add(user.getLogin());
        if (isFirstUser){
            request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        }


        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(ROLE, user.getRole()); // todo without role and login only user entity
        //request.getSession().setAttribute(EMAIL, user.getEmail());
        request.getSession().setAttribute(LOGIN, user.getLogin()); // todo without role and login only user entity
        request.setAttribute(LOGIN, user.getLogin());
        logger.info(user.getLogin() + LOGGED_SUCCESSFULLY_WITH_ROLE + user.getRole());//Account.Role.valueOf(user.getRole())
    }

    public static void loggingEnds(Object obj, Logger logger) {
        logger.info(obj.getClass().getSimpleName() + ENDS);
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String login) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers"); // todo getSession().getServletContext() on session or on request ???
        if(loggedUsers == null){
            loggedUsers = new HashSet<>();
        }
        return loggedUsers.stream().anyMatch(login::equals);
    }
}
