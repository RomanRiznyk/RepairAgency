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

        String sortingType = request.getParameter("sortingType");



        String page = request.getParameter("page");



        HttpSession session = request.getSession();
        List<User> masterList = masterService.getAllMasters();
        List<String> statusList = Arrays.asList("Waiting for payment", "Paid", "Canceled", "In work", "Done", "All statuses"); // todo from Enum avoid this aarray HARDCODE!!!
        List<String> sortByList = Arrays.asList("ByDate", "ByStatus", "ByPrice");
        List<String> sortByAsc = Arrays.asList("Ascending", "Descending");

        List<Receipt> receiptList = new ArrayList<>();
        if (request.getParameter("receiptListForLang") == null) {
            if (request.getParameter("sortType") != null) {
                receiptList = getSortedReceipts(request);
            } else if (request.getParameter("filterType") != null) {
                receiptList = getFilteredReceipts(request);
            } else {
                receiptList = managerService.getAllReceipts();
            }

           // receiptList = getFilteredReceipts(request);
            //new TableSorter().sort(sortingType, receiptList); // todo avoid new


        } else {
            getReceiptListFromRequestParameter(request, receiptList);
        }

        int pages = new PageCounter().count(receiptList.size(), ROWS_ON_PAGE);
        List<Receipt> pageReceiptList = new ListSplitter().getListByPage(page, ROWS_ON_PAGE, receiptList); // todo Di

        request.setAttribute("sortBy", sortByList); // todo Builder?  sortByAsc
        request.setAttribute("sortByAsc", sortByAsc);

        request.setAttribute("pages", pages);
        session.removeAttribute("receiptList");
        session.setAttribute("receiptList", pageReceiptList);
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
//            boolean isStatusAscending = Boolean.parseBoolean(request.getParameter("isAscending"));
            return managerService.getReceiptsByStatus(status); // todo Optional
        }

        if (masterLogin != null && !masterLogin.equals("All masters")) {
//            boolean isMasterAscending = Boolean.parseBoolean(request.getParameter("isAscending"));
            return masterService.getReceiptsByMaster(masterLogin); // todo Optional and why this is masterservice???
        }
        return managerService.getAllReceipts(); // todo Optional rename select
    }

//    public List<Receipt> getFilteredByStatus(String status) { // todo refactor name
//
//        List<Receipt> receiptList = new ArrayList<>();
//        if (status != null && !status.equals("All status")) {
//            receiptList = managerService.getReceiptsByStatus(status); // todo Optional
//        } else if (masterLogin != null && !masterLogin.equals("All")) {
//            receiptList = masterService.getReceiptsByMaster(masterLogin); // todo Optional and why this is masterservice???
//        } else {
//            receiptList = managerService.getAllReceipts(); // todo Optional rename select
//        }
//        return receiptList;
//    }

    public List<Receipt> getSortedReceipts(HttpServletRequest request) {
        String ascType = "ASC";
        if ("Descending".equals(request.getParameter("sortAscDesc"))) {
            ascType = "DESC";
        }
        System.out.println(" ASC TYPE = " + ascType);
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
