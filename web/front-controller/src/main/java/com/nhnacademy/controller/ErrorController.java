package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static jakarta.servlet.RequestDispatcher.*;

public class ErrorController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("status_code", req.getAttribute(ERROR_STATUS_CODE));

        req.setAttribute("exception_type", ERROR_EXCEPTION_TYPE);

        req.setAttribute("exception_message", ERROR_MESSAGE);

        req.setAttribute("exception", ERROR_EXCEPTION);

        req.setAttribute("exception_request_uri", ERROR_REQUEST_URI);

        return "/error.jsp";
    }
}
