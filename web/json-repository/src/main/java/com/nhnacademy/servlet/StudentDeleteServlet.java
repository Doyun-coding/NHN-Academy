package com.nhnacademy.servlet;

import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Student;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "studentDeleteServlet", value = "/student/delete")
public class StudentDeleteServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("removeId");
        log.debug("id={}", id);

        studentRepository.deleteById(id);

//        String url = "/student_war_exploded/student/list";
//        resp.sendRedirect(url);

        //todo view attribute - redirect:/student/list.jsp
        req.setAttribute("view", "redirect: list.do");
    }

}
