package com.repair.agency.controller.command;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.utils.ValidationUtil;
import com.repair.agency.model.service.UserService;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginProcessCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginProcessCommand.class.getName());
    private static final String SHOULD_BE_NOT_NULL = "Login or password cannot be empty or null";
    private static final String IS_ABSENT = " is absent, try again or register new user";
    private static final String INVALID_PASSWORD = "Invalid password, try again";
    private final UserService userService;

    public LoginProcessCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts"); // todo ValidationUtil.loggerStart and Ends
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        //todo extract to validation:

        if( login == null || login.equals("") || password == null || password.isEmpty()){ //todo what's better isEmpty or equals("")
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_NOT_NULL);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.LOGIN_PAGE;
        }

        if(ValidationUtil.checkUserIsLogged(request, login)){
            request.setAttribute("errorMessage", "LoggedOnOtherDevice");
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.LOGIN_PAGE;
        }

        Optional<User> user = userService.findUserByLogin(login); // todo external method .orElse()
        if (!user.isPresent()) {
            ValidationUtil.processInvalidCredentials(request, "\"" +login + "\"" + IS_ABSENT);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.LOGIN_PAGE;
        }

        if (!password.equals(user.get().getPassword())) {
            ValidationUtil.processInvalidCredentials(request, INVALID_PASSWORD);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.LOGIN_PAGE;
        }

        ValidationUtil.addUserIntoSessionAndContext(request, logger, user.get());
        ValidationUtil.loggingEnds(this, logger);

        switch (user.get().getRole()) {
            case "MANAGER":
                return "redirect:" + "/manager";
            case "MASTER":
                return "redirect:" + "/master";
            case "USER":
                return "redirect:" + "/user";

            default:
                ValidationUtil.loggingEnds(this, logger);
                return PathConstants.ERROR_LOGIN;
        }
    }
}
