package com.repair.agency.controller.command.manager;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.controller.command.LoginProcessCommand;
import com.repair.agency.controller.utils.PaginationUtil;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.entity.Receipt;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerReceiptListCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginProcessCommand.class.getName());
    MasterService masterService;
    ManagerService managerService;

    public ManagerReceiptListCommand(MasterService masterService, ManagerService managerService) {
        this.masterService = masterService;
        this.managerService = managerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info(this.getClass().getSimpleName() + " --> starts");
        HttpSession session = request.getSession();
        List<User> masterList = masterService.getAllMasters();
        List<String> statusList = Arrays.asList("Waiting for payment", "Paid", "Canceled", "In work", "Done", "All statuses");
        List<String> sortByList = Arrays.asList("ByDate", "ByStatus", "ByPrice");
        List<String> sortByAsc = Arrays.asList("Ascending", "Descending");
        List<String> rowNumbers = Arrays.asList("3", "5", "10");
        List<Receipt> receiptList = new ArrayList<>();
        String page = request.getParameter("page");
        String rowNumber = request.getParameter("rowNumber");
        System.out.println("ROW NUMBER FROM REQUEST = " + rowNumber);
        if (rowNumber != null) {
            session.removeAttribute("rowNumber");
            session.setAttribute("rowNumber", rowNumber);
        } else {
            if (session.getAttribute("rowNumber") == null){
                rowNumber = "5";
            } else {
                rowNumber = (String) session.getAttribute("rowNumber");
                System.out.println("ROW NUMBER FROM SESSION = " + rowNumber);
            }
            System.out.println("ROW NUMBER FROM DEFAULT = " + rowNumber);
        }
        if (page == null) {
            if (request.getParameter("receiptListForLang") == null) {
                if (request.getParameter("sortType") != null) {
                    receiptList = getSortedReceipts(request);
                } else if (request.getParameter("filterType") != null) {
                    receiptList = getFilteredReceipts(request);
                } else {
                    receiptList = managerService.getAllReceipts();
                }
            } else {
                getReceiptListFromRequestParameter(request, receiptList);
            }
            session.removeAttribute("receiptList");
            session.setAttribute("receiptList", receiptList);
        }
        receiptList = (List<Receipt>) session.getAttribute("receiptList");
        int pages = PaginationUtil.countPages(receiptList.size(), Integer.parseInt(rowNumber));
        List<Receipt> pageReceiptList = PaginationUtil.getListByPage(page, Integer.parseInt(rowNumber), receiptList);
        request.setAttribute("rowNumbers", rowNumbers);
        System.out.println("ROWNUMBERS LIST = " + rowNumbers);
        request.setAttribute("sortBy", sortByList); // todo Builder?  sortByAsc
        request.setAttribute("sortByAsc", sortByAsc);
        request.setAttribute("pages", pages);
        request.setAttribute("pageReceiptList", pageReceiptList);
        request.setAttribute("statusList", statusList);
        request.setAttribute("masterList", masterList);
        logger.info(this.getClass().getSimpleName() + " --> ends");
        return PathConstants.MANAGER_PAGE;
    }

    private void getReceiptListFromRequestParameter(HttpServletRequest request, List<Receipt> receiptList) {
        String receiptListString = request.getParameter("receiptListForLang");
        String[] receiptsStrings = receiptListString.split("Receipt\\{id=");
        ArrayList<Integer> ids = new ArrayList<>();
        for(String receipt: receiptsStrings) {
            if (receipt.charAt(0) != '[') {
                ids.add(Integer.parseInt(String.valueOf(receipt.charAt(0))));
            }
        }
        for(Integer id: ids) {
            receiptList.add(managerService.getReceipt(id));
        }
    }

    private List<Receipt> getFilteredReceipts(HttpServletRequest request) { // todo refactor name
        String status = request.getParameter("status");
        String masterLogin = request.getParameter("masterLogin");

        if (status != null && !status.equals("All statuses")) {
            return managerService.getReceiptsByStatus(status); // todo Optional
        }

        if (masterLogin != null && !masterLogin.equals("All masters")) {
            return masterService.getReceiptsByMaster(masterLogin); // todo Optional and why this is masterservice???
        }
        return managerService.getAllReceipts(); // todo Optional rename select
    }

    public List<Receipt> getSortedReceipts(HttpServletRequest request) {
        String ascType = "ASC";
        if ("Descending".equals(request.getParameter("sortAscDesc"))) {
            ascType = "DESC";
        }
        switch (request.getParameter("sortType")) {
            case "ByDate":
                System.out.println("SORT TYPE = " + request.getParameter("sortType"));
                return managerService.getAllReceiptsSortedByDate(ascType);
            case "ByStatus":
                System.out.println("SORT TYPE = " + request.getParameter("sortType"));
                return managerService.getAllReceiptsSortedByStatus(ascType);
            case "ByPrice":
                System.out.println("SORT TYPE = " + request.getParameter("sortType"));
                return managerService.getAllReceiptsSortedByPrice(ascType);
            default:
                System.out.println("SORT TYPE = " + request.getParameter("sortType"));
                return managerService.getAllReceipts();
        }
    }
}
