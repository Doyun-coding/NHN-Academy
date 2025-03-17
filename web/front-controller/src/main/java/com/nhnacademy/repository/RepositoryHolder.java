package com.nhnacademy.repository;

import com.nhnacademy.student.Student;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 여기서는 Singleton 방식으로 사용하지 않고 Servlet 마다의 Repository 를 사용
public class RepositoryHolder {
    private static final StudentRepository studentRepository = new MapStudentRepository();

    public static synchronized MapStudentRepository getStudentRepository() {
        return (MapStudentRepository) studentRepository;
    }

}
