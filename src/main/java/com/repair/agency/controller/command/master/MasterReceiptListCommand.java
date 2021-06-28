package com.repair.agency.controller.command.master;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.ErrorPageCommand;
import com.repair.agency.controller.command.LoginProcessCommand;
import com.repair.agency.model.exceptionhandler.DBException;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.controller.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MasterReceiptListCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginProcessCommand.class.getName());
    public static final int ROWS_ON_PAGE = 10;
    MasterService masterService;

    public MasterReceiptListCommand(MasterService masterService) {
        this.masterService = masterService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");
        String login = (String) request.getSession().getAttribute("login");
        String page = request.getParameter("page");
        String newStatus = request.getParameter("newStatus");
        if (newStatus != null) {
            try {
                masterService.updateStatus(Integer.parseInt(request.getParameter("receiptId")), newStatus);
            } catch (DBException ex) {
                request.setAttribute("errorMessage", ex.getMessage());
                logger.debug("Cannot update status", ex); // todo
                ex.printStackTrace();
                return new ErrorPageCommand().execute(request,response);
            }
        }

        List<Receipt> receiptList = masterService.getReceiptsByMaster(login);
        int pages = PaginationUtil.countPages(receiptList.size(), ROWS_ON_PAGE);
        List<Receipt> pageReceiptList = PaginationUtil.getListByPage(page, ROWS_ON_PAGE, receiptList);
        request.setAttribute("pages", pages);
        request.setAttribute("receiptList", pageReceiptList);
        logger.info(this.getClass().getSimpleName() + " --> ends");
        return PathConstants.MASTER_PAGE;
    }
}

