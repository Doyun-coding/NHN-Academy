package com.nhnacademy.servlet;

import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Gender;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@WebServlet(name = "studentRegisterServlet", value = "/student/register")
public class StudentRegisterServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String url = "/student/register.jsp";
//
//        RequestDispatcher rd = req.getRequestDispatcher(url);
//        rd.forward(req, resp);

        req.setAttribute("view", "/student/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Post Register");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String g = String.valueOf(req.getParameter("gender"));
        Gender gender;
        if(g.equals("M")) {
            gender = Gender.M;
        }
        else {
            gender = Gender.F;
        }
        int age = Integer.parseInt(String.valueOf(req.getParameter("age")));

        if(Objects.isNull(id) || Objects.isNull(name) || Objects.isNull(g) || Objects.isNull(age) || id.equals("") || name.equals("") || g.equals("") || age <= 0) {
            throw new RuntimeException("입력 오류!!");
        }

        if(studentRepository.getStudentById(id) != null) {
            req.setAttribute("error_message", "이미 같은 아이디가 존재합니다!");

//            String url = "/student/register.jsp";
//
//            RequestDispatcher rd = req.getRequestDispatcher(url);
//            rd.forward(req, resp);
            req.setAttribute("view", "view.do?id=" + id);
        }
        else {

            log.debug("id={}", id);
            log.debug("name={}", name);
            log.debug("g={}", g);
            log.debug("age={}", age);

            studentRepository.save(new Student(id, name, gender, age));
//            Student student = studentRepository.getStudentById(id);
//            String date = String.valueOf(student.getCreateAt());
//
//            String url = "/student_war_exploded/student/view?id=" + id + "&name=" + name + "&gender=" + gender + "&age=" + age + "&date=" + date;
//            resp.sendRedirect(url);

            req.setAttribute("view", "redirect: view.do?id=" + id);
        }
    }

}
