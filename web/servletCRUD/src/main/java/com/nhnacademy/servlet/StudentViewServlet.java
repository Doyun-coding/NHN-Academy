package com.nhnacademy.servlet;

import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "studentViewServlet", value = "/student/view")
public class StudentViewServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        String name = student.getName();
        String gender = String.valueOf(student.getGender());
        String age = String.valueOf(student.getAge());
        String date = String.valueOf(student.getCreateAt());

        log.debug("id:{}", id);
        log.debug("name:{}", name);
        log.debug("gender:{}",gender);
        log.debug("age:{}", age);
        log.debug("date:{}", date);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("id", id);
        httpSession.setAttribute("name", name);
        httpSession.setAttribute("gender", gender);
        httpSession.setAttribute("age", age);
        httpSession.setAttribute("date", date);

        req.setAttribute("update_link", "update?id=" + id);

        String url = "/student/view.jsp";

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);

    }

}
