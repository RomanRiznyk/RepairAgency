package com.repair.agency.controller.command.user;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.manager.EditReceiptCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewReceiptPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        logger.info(this.getClass().getSimpleName() + " --> ends");
        return PathConstants.USER_NEW_RECEIPT_PAGE;
    }
}
