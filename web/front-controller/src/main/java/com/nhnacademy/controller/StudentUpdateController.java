package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentUpdateController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);
        req.setAttribute("student", student);

        return "/student/register.jsp";
    }
}
