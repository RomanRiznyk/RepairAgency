package com.repair.agency.controller.command;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import com.repair.agency.controller.utils.ValidationUtil;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationProcessCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    private static final String SHOULD_BE_NOT_NULL = "ShouldBeNotNull";
    private static final String SHOULD_BE_VALID_LOGIN = "ShouldBeValidLogin";
    private static final String SHOULD_BE_VALID_PASSWORD = "ShouldBeValidPassword";
    private static final String SHOULD_BE_VALID_EMAIL = "ShouldBeValidEmail";
    private static final String ALREADY_EXIST = "AlreadyExist";
    private static final String SHOULD_BE_EQUALS = "ShouldBeEquals";
    private final UserService userService;

    public RegistrationProcessCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        if( login == null || login.equals("") || password == null || password.isEmpty()){
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_NOT_NULL);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        if(!login.matches("^[A-z\\p{IsCyrillic}0-9._-]{3,}$")){
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_VALID_LOGIN);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        if(!password.matches("^[a-zA-Z0-9._-]{3,}$")){
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_VALID_PASSWORD);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        if (!email.matches("^(.+)@(.+)\\.(.+)$") &  !(email == null || "".equals(email))) {
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_VALID_EMAIL);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        if (!password.equals(confirmPassword)) {
            ValidationUtil.processInvalidCredentials(request, SHOULD_BE_EQUALS);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        Optional<User> newUser =  (email == null || "".equals(email)) ? userService.createUser(login, password) : userService.createUserWithMail(login, password, email);
        if (!newUser.isPresent()) {
            ValidationUtil.processInvalidCredentials(request, "\"" +login + "\"" + ALREADY_EXIST);
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.REGISTER_PAGE;
        }
        System.out.println("NEW USER LOGIN = " + newUser.get().getLogin());
        ValidationUtil.addUserIntoSessionAndContext(request, logger, newUser.get());
        request.setAttribute("registerSuccess", "true");
        ValidationUtil.loggingEnds(this, logger);

        return newUser.isPresent() ? "redirect:" + "/user" : "redirect:" + "/error";
    }
}
