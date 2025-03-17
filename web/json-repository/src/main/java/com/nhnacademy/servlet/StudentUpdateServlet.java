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
import java.util.Objects;

@Slf4j
@WebServlet(name = "studentUpdateServlet", value = "/student/update")
public class StudentUpdateServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        req.setAttribute("student", student);

        HttpSession session = req.getSession();
        session.setAttribute("removeId", id);

//        String url = "/student/register.jsp";
//
//        RequestDispatcher rd = req.getRequestDispatcher(url);
//        rd.forward(req, resp);

        req.setAttribute("view", "/student/register.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("removeId");
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
            throw new IllegalArgumentException("입력 오류!!");
        }

        studentRepository.save(new Student(id, name, gender, age));
        Student student = studentRepository.getStudentById(id);
        String date = String.valueOf(student.getCreateAt());

        session.removeAttribute("removeId");

//        String url = "/student_war_exploded/student/view?id=" + id + "&name=" + name + "&gender=" + gender + "&age=" + age + "&date=" + date;
//        resp.sendRedirect(url);

        req.setAttribute("view", "redirect: view.do?id=" + id);

    }

}
