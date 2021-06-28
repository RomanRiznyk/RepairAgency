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

public class AddFeedbackCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    UserService userService;

    public AddFeedbackCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        String feedback = request.getParameter("feedback");
        String id = request.getParameter("id");

        if (id == null) {
            logger.info("ERROR in ID: " + id); // todo Logger level and how to
            return PathConstants.ERROR_PAGE;  // todo exHandler
        }
        if (!userService.addFeedback(Integer.parseInt(id), feedback)) {
            logger.info("Error in add feedback");
            ValidationUtil.loggingEnds(this, logger);
            return PathConstants.ERROR_PAGE;  // todo exHandler
        }
        ValidationUtil.loggingEnds(this, logger);
        return "redirect:" + "/user";
    }
}
