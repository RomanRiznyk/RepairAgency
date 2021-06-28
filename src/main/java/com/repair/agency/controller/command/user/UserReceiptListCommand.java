package com.repair.agency.controller.command.user;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import com.repair.agency.controller.utils.ValidationUtil;
import com.repair.agency.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserReceiptListCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    UserService userService;

    public UserReceiptListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(this.getClass().getSimpleName() + " --> starts"); // todo refactor this int logger ValidationUtil methods class
        String login = (String) request.getSession().getAttribute("login"); // todo all this constants extract into individual Contsant class

        request.setAttribute("login", login);
        request.setAttribute("balance", userService.getBalance(login));  //todo Optional and redirect(?) on error
        request.setAttribute("receiptList", userService.getReceiptsByLogin(login)); //todo Optional and redirect(?) on error
        ValidationUtil.loggingEnds(this, logger);
        /*if ("processNewReceipt".equals(request.getAttribute("command"))) {
            System.out.println("REMOVE ATTRIBUTE");
            request.removeAttribute("command");
            request.removeAttribute("item");
            request.removeAttribute("description");
        }*/
        return PathConstants.USER_PAGE;
    }
}
