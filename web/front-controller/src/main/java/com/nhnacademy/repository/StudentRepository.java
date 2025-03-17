package com.nhnacademy.repository;

import com.nhnacademy.student.Student;

import java.util.List;

public interface StudentRepository {

    void save(Student student);
    void update(Student student);
    void deleteById(String id);
    Student getStudentById(String id);
    List<Student> getStudents();
    boolean existById(String id);

}
