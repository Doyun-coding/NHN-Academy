package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Gender;
import com.nhnacademy.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class StudentUpdateFormController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        String updateId = session.getAttribute("updateId").toString();

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

        studentRepository.update(new Student(updateId, name, gender, age));

        session.removeAttribute("updateId");

        return "redirect: view.do?id=" + updateId;
    }
}
