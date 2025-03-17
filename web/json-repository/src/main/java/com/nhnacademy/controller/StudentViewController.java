package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StudentViewController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        String name = student.getName();
        String gender = String.valueOf(student.getGender());
        String age = String.valueOf(student.getAge());
        String date = String.valueOf(student.getCreateAt());

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("id", id);
        httpSession.setAttribute("name", name);
        httpSession.setAttribute("gender", gender);
        httpSession.setAttribute("age", age);
        httpSession.setAttribute("date", date);

        req.setAttribute("update_link", "update.do?id=" + id);

        return "/student/view.jsp";
    }
}
