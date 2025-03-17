package com.nhnacademy.servlet;

import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet(name = "studentListServlet", value = "/student/list")
public class StudentListServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> list = studentRepository.getStudents();
        req.setAttribute("studentList", list);

//        String url = "/student/list.jsp";
//
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher(url);
//        requestDispatcher.forward(req, resp);

        req.setAttribute("view", "/student/list.jsp");

    }

}
