package com.nhnacademy.listener;

import com.nhnacademy.repository.JsonStudentRepository;
import com.nhnacademy.repository.MapStudentRepository;
import com.nhnacademy.repository.RepositoryHolder;
import com.nhnacademy.repository.StudentRepository;
import com.nhnacademy.student.Gender;
import com.nhnacademy.student.Student;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Random;

@WebListener
public class WebApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        StudentRepository studentRepository = new JsonStudentRepository();

        for(int i = 1; i <= 10; i++) {
            String id = "student";
            id += String.valueOf(i);

            String name = "아카데미";
            name += String.valueOf(i);

            Random random = new Random();
            int age = random.nextInt(11)+20;

            Gender gender;
            int r = random.nextInt(2);
            if(r == 0) {
                gender = Gender.M;
            }
            else {
                gender = Gender.F;
            }

            Student student = new Student(id, name, gender, age);
            studentRepository.save(student);
        }

        context.setAttribute("studentRepository", studentRepository);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
