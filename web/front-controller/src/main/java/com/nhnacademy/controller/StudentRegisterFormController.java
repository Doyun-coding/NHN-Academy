package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Gender;
import com.nhnacademy.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class StudentRegisterFormController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
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

            return "/student/register.jsp";
        }
        else {
            studentRepository.save(new Student(id, name, gender, age));

            return "redirect: view.do?id=" + id;
        }

    }
}
