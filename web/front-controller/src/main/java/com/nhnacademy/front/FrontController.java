package com.nhnacademy.front;

import com.nhnacademy.command.Command;
import com.nhnacademy.controller.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "frontController", value = "*.do")
public class FrontController extends HttpServlet {
    private static final String REDIRECT_PREFIX = "redirect";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        try {
            Command command = resolveCommand(req.getServletPath(), req.getMethod());
            String view = command.execute(req, resp);

            if(view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length() + 1));

                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()+1));
            }
            else {
                RequestDispatcher rd = req.getRequestDispatcher(view);
                rd.include(req, resp);
            }

        }
        catch(Exception ex) {
            req.setAttribute("exception", ex);

            ErrorController errorController = new ErrorController();
            String view = errorController.execute(req, resp);

            RequestDispatcher rd = req.getRequestDispatcher(view);
            rd.forward(req, resp);
        }

    }

    private Command resolveCommand(String servletPath, String method) {
        Command command = null;
        if(servletPath.equals("/student/list.do") && method.equals("GET")) {
            command = new StudentListController();
        }
        if(servletPath.equals("/student/delete.do") && method.equals("POST")) {
            command = new StudentDeleteController();
        }
        if(servletPath.equals("/student/register.do") && method.equals("GET")) {
            command = new StudentRegisterController();
        }
        if(servletPath.equals("/student/register.do") && method.equals("POST")) {
            command = new StudentRegisterFormController();
        }
        if(servletPath.equals("/student/update.do") && method.equals("GET")) {
            command = new StudentUpdateController();
        }
        if(servletPath.equals("/student/update.do") && method.equals("POST")) {
            command = new StudentUpdateFormController();
        }
        if(servletPath.equals("/student/view.do") && method.equals("GET")) {
            command = new StudentViewController();
        }

        return command;
    }

}
