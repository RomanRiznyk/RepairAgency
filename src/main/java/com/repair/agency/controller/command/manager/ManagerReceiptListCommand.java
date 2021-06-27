package com.repair.agency.controller.command.manager;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.ValidationCommand;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import com.repair.agency.model.utils.ListSplitter;
import com.repair.agency.model.utils.PageCounter;
import com.repair.agency.model.utils.TableSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerReceiptListCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ValidationCommand.class.getName());
    public static final int ROWS_ON_PAGE = 10;
    MasterService masterService;
    ManagerService managerService;

    public ManagerReceiptListCommand(MasterService masterService, ManagerService managerService) {
        this.masterService = masterService;
        this.managerService = managerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(this.getClass().getSimpleName() + " --> starts");

        String status = request.getParameter("status");
        String masterLogin = request.getParameter("masterLogin");
        String sortingType = request.getParameter("sortingType");
        String page = request.getParameter("page");
        HttpSession session = request.getSession();

        List<User> masterList = masterService.getAllMasters();
        List<String> statusList = Arrays.asList("Waiting for payment", "Paid", "Canceled", "In work", "Done", "All status"); // todo from Enum avoid this aarray HARDCODE!!!
        List<String> sortByList = Arrays.asList("ByDate", "ByStatus", "ByPrice");
        List<Receipt> receiptList = new ArrayList<>();
        if (request.getParameter("receiptListForLang") == null) {
            receiptList = getFilteredReceipts(status, masterLogin);
            if (sortingType != null) {
                session.setAttribute("sort", sortingType); // todo why Session
            }
        new TableSorter().sort((String) session.getAttribute("sort"), receiptList); // todo avoid new
        }

        int pages = new PageCounter().count(receiptList.size(), ROWS_ON_PAGE);
        List<Receipt> pageReceiptList = new ListSplitter().getListByPage(page, ROWS_ON_PAGE, receiptList); // todo Di

        request.setAttribute("sortBy", sortByList); // todo Builder?
        request.setAttribute("pages", pages);
        request.setAttribute("receiptList", pageReceiptList);
        request.setAttribute("statusList", statusList);
        request.setAttribute("masterList", masterList);

        logger.info(this.getClass().getSimpleName() + " --> ends");
        return PathConstants.MANAGER_PAGE;
    }

    public List<Receipt> getFilteredReceipts(String status, String masterLogin) { // todo refactor name

        List<Receipt> receiptList = new ArrayList<>();
        if (status != null && !status.equals("All status")) {
            receiptList = managerService.getReceiptsByStatus(status); // todo Optional
        } else if (masterLogin != null && !masterLogin.equals("All")) {
            receiptList = masterService.getAllReceipts(masterLogin); // todo Optional and why this is masterservice???
        } else {
            receiptList = managerService.getAllReceipts(); // todo Optional rename select
        }
        return receiptList;
    }
}
