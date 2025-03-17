package com.nhnacademy.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static jakarta.servlet.RequestDispatcher.*;

@Slf4j
@WebServlet(name = "errorServlet", value = "/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("status_code", req.getAttribute(ERROR_STATUS_CODE));

        log.debug("Error Message:{}", req.getAttribute(ERROR_STATUS_CODE));

        //todo exception_type
        req.setAttribute("exception_type", ERROR_EXCEPTION_TYPE);

        //todo message
        req.setAttribute("exception_message", ERROR_MESSAGE);

        //todo exception
        req.setAttribute("exception", ERROR_EXCEPTION);

        //todo request_uri
        req.setAttribute("exception_request_uri", ERROR_REQUEST_URI);

        //todo /error.jsp forward 처리
        String url = "/error.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);

    }

}
