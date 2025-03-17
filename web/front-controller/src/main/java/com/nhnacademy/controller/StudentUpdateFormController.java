package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Gender;
import com.nhnacademy.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

public class StudentUpdateFormController implements Command {
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
            throw new IllegalArgumentException("입력 오류!!");
        }

        studentRepository.save(new Student(id, name, gender, age));

        return "redirect: view.do?id=" + id;
    }
}
