package com.repair.agency.controller.command.manager;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.ErrorPageCommand;
import com.repair.agency.model.exceptionhandler.DBException;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.service.UserService;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public class EditReceiptCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    ManagerService managerService;
    UserService userService;
    MasterService masterService;

    public EditReceiptCommand(ManagerService managerService, UserService userService, MasterService masterService) {
        this.managerService = managerService;
        this.masterService = masterService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.info(this.getClass().getSimpleName() + " --> starts");

        String masterId = request.getParameter("masterId");
        String price = request.getParameter("price");
        String status = request.getParameter("status");
        String newBalance = request.getParameter("newBalance");
        String userLogin = request.getParameter("userLogin");
        if (request.getParameter("receiptID") == null) {
            return new ErrorPageCommand().execute(request,response);
        }
        int receiptID = Integer.parseInt(request.getParameter("receiptID"));
        String additionalBalance = request.getParameter("add balance");
        Receipt receipt = managerService.getReceipt(receiptID);
        BigDecimal balance = userService.getBalance(receipt.getUserLogin());

        if (!"".equals(additionalBalance) && additionalBalance != null) {
            String login = receipt.getUserLogin();
            managerService.updateUserAddBalance(login, balance, new BigDecimal(additionalBalance));
        }
        if (newBalance != null) {
                if (userService.updateBalanceByLogin(userLogin, new BigDecimal(newBalance))) {
                    status = "Paid";
                    request.setAttribute("paidSuccess", "true");
                }
        }
        if (masterId != null) {
            managerService.updateReceiptMaster(Integer.parseInt(masterId), receiptID);
        }
        if (price != null) {
            managerService.updateReceiptPrice(price, receiptID);
        }
        if (status != null) {
            if ("Paid".equals(request.getParameter("oldStatus"))) {
                try {
                    managerService.updateStatusAndReturnMoney(receiptID, status);
                } catch (DBException ex) {
                    request.setAttribute("errorMessage", ex.getMessage());
                    return new ErrorPageCommand().execute(request,response);
                }
            }
            try {
                managerService.updateReceiptStatus(receiptID, status);
            } catch (DBException ex) {
                request.setAttribute("errorMessage", ex.getMessage());
                return new ErrorPageCommand().execute(request,response);
            }
        }
        balance = userService.getBalance(receipt.getUserLogin());
        receipt = managerService.getReceipt(receiptID);
        request.setAttribute("receipt", receipt);
        request.setAttribute("balance", balance);
        List<User> masterList = masterService.getAllMasters();
        request.setAttribute("masterList", masterList);
        logger.info(this.getClass().getSimpleName() + " --> ends");
        return PathConstants.MANAGER_EDIT_RECEIPT_PAGE;
    }
}

