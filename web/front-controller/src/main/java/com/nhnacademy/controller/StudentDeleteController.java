package com.nhnacademy.controller;

import com.nhnacademy.command.Command;
import com.nhnacademy.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentDeleteController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        String id = req.getParameter("removeId");

        log.error("id:{}", id);

        studentRepository.deleteById(id);

        return "redirect: list.do";
    }
}
