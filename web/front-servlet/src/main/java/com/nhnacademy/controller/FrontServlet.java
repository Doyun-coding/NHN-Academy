package com.nhnacademy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "frontServlet", value = "*.do")
public class FrontServlet extends HttpServlet {
    private  static final String REDIRECT_PREFIX = "redirect";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //todo 공통 처리 - 응답 content-type, character encoding 지정.
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try {
            log.debug("getServletPath={}", req.getServletPath());
            // 매핑 서블릿을 결정
            String servletPath = resolveServlet(req.getServletPath());
            RequestDispatcher rd = req.getRequestDispatcher(servletPath);
            rd.include(req, resp);

            // 서블릿으로 부터 view 전달받는다
            String view = (String) req.getAttribute("view");
            log.debug("view={}", view);
            if(view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()+1));

                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()+1));
            }
            else {
                log.debug("Not Redirect-url");
                rd = req.getRequestDispatcher(view);
                rd.include(req, resp);

            }

        }
        catch(Exception ex){
            //todo 공통 error 처리 - ErrorServlet 참고해서 처리
            //todo  forward - /error.jsp
            req.setAttribute("exception", ex);
            RequestDispatcher rd = req.getRequestDispatcher("/error");
            rd.forward(req, resp);
        }
    }

    private String resolveServlet(String servletPath) {
        String processingServlet = null;
        if(servletPath.equals("/student/list.do")) {
            processingServlet = "/student/list";
        }
        if(servletPath.equals("/student/register.do")) {
            processingServlet = "/student/register";
        }
        if(servletPath.equals("/student/update.do")) {
            processingServlet = "/student/update";
        }
        if(servletPath.equals("/student/delete.do")) {
            processingServlet = "/student/delete";
        }
        if(servletPath.equals("/student/view.do")) {
            processingServlet = "/student/view";
        }

        return processingServlet;
    }

}
