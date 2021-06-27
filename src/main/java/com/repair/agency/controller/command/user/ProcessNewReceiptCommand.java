package com.repair.agency.controller.command.user;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.service.UserService;
import com.repair.agency.model.entity.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProcessNewReceiptCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    UserService userService;

    public ProcessNewReceiptCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        String login = (String) request.getSession().getAttribute("login");
        String item = request.getParameter("item");
        String description = request.getParameter("description");
        String masterLogin = request.getParameter("masterLogin");

        if (masterLogin != null && !masterLogin.equals("")){
            if (!userService.createReceiptWithMaster(login, item, description, masterLogin)) {  // todo Optional
                return PathConstants.ERROR_PAGE;
            }
        }

        if (!userService.createReceipt(login, item, description)) {  // todo Optional
            logger.info(" PROCESS NEW RECEIPT COMMAND - createReceipt FAILED"); // todo Error handler
            return PathConstants.ERROR_PAGE;
        }

        List<Receipt> receiptList = userService.getReceiptsByLogin((String) request.getSession().getAttribute("login"));
        request.setAttribute("receiptList", receiptList); //todo check if changed on jsp
        request.setAttribute("successMessage", "true");
        logger.info(this.getClass().getSimpleName() + " --> ends");
        //return "redirect:" + PathConstants.USER_PAGE;
        //return "redirect:" + new UserReceiptListCommand().execute(request, response);
        return "redirect:" + "/user";

    }
}
