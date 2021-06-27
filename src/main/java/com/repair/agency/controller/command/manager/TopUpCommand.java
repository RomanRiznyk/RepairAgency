package com.repair.agency.controller.command.manager;

import com.repair.agency.PathConstants;
import com.repair.agency.controller.command.Command;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TopUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReceiptCommand.class.getName());
    ManagerService managerService;

    public TopUpCommand(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        /*logger.info(this.getClass().getSimpleName() + " --> starts");
        String balance = request.getParameter("usersWallet");
        String usersId = request.getParameter("usersId");
        String usersLogin = request.getParameter("usersLogin");
        String editUsersWallet = request.getParameter("usersEditWallet");

        if (editUsersWallet != null && usersId != null) {
           boolean result = managerService.setUsersWallet(usersId, editUsersWallet);
           balance = result ? editUsersWallet + ".00" : "input mistake";
        }
        if (balance == null) {
            balance = "no user selected";
        }
        if (usersLogin == null) {
            usersLogin = "User";
        }
        if (!(usersId == null)) {
            request.setAttribute("currentId", usersId);
        }
        List<User> users = managerService.getAllUsers();

        request.setAttribute("currentUser", usersLogin);
        request.setAttribute("currentWallet", balance);
        request.setAttribute("users", users);
        logger.info(this.getClass().getSimpleName() + " --> ends");*/
        return PathConstants.TOP_UP_PAGE;
    }
}
