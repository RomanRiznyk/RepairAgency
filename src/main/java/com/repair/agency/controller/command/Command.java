package com.repair.agency.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
