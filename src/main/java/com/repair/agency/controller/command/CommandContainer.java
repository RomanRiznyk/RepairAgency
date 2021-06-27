package com.repair.agency.controller.command;

import com.repair.agency.controller.command.manager.*;
import com.repair.agency.controller.command.master.MasterReceiptListCommand;
import com.repair.agency.controller.command.user.*;
import com.repair.agency.controller.command.user.UserReceiptListCommand;
import com.repair.agency.model.service.ManagerService;
import com.repair.agency.model.service.MasterService;
import com.repair.agency.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private final Map<String, Command> commands;

    public CommandContainer() {// todo static
        commands = new HashMap<>();
        commands.put("/logout", new LogoutCommand());
        commands.put("/login", new LoginPageCommand());
        commands.put("/register", new RegisterPageCommand());
        commands.put("/account", new ValidationCommand(new UserService()));
        commands.put("/user/account", new ValidationCommand(new UserService()));
        commands.put("/manager/account", new ValidationCommand(new UserService()));
        //commands.put("/view/account", new ValidationCommand(new UserService()));
        commands.put("/master/account", new ValidationCommand(new UserService()));
        commands.put("/processRegister", new ProcessRegistrationCommand(new UserService()));
        commands.put("/manager/editReceipt", new EditReceiptCommand(new ManagerService(), new UserService(), new MasterService()));
        commands.put("/manager", new ManagerReceiptListCommand(new MasterService(), new ManagerService()));
        commands.put("/master", new MasterReceiptListCommand(new MasterService()));
        commands.put("/error", new ErrorPageCommand());
        commands.put("/user/processNewReceipt", new ProcessNewReceiptCommand(new UserService()));
        commands.put("/user/new_order", new NewReceiptPageCommand());
        commands.put("/user", new UserReceiptListCommand(new UserService()));
        commands.put("/user/feedback", new FeedbackCommand());
        commands.put("/user/addFeedback", new AddFeedbackCommand(new UserService()));
    }

    public Command get(String commandName) {
        return commands.getOrDefault(commandName, (r,t)->"/error");
    }
}
