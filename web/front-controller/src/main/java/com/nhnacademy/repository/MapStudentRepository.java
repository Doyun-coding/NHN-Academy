package com.nhnacademy.repository;

import com.nhnacademy.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStudentRepository implements StudentRepository {
    private Map<String, Student> studentMap;

    public MapStudentRepository() {
        this.studentMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Student student) {
        if(studentMap.containsKey(student.getId())) {
            studentMap.replace(student.getId(), student);
            return;
        }

        studentMap.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        if(studentMap.containsKey(student.getId())) {
            studentMap.replace(student.getId(), student);
        }
    }

    @Override
    public void deleteById(String id) {
        if(studentMap.containsKey(id)) {
            studentMap.remove(id);
        }
    }

    @Override
    public Student getStudentById(String id) {
        if(studentMap.containsKey(id)) {
            return studentMap.get(id);
        }

        return null;
    }

    @Override
    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();

        for(String key : studentMap.keySet()) {
            list.add(getStudentById(key));
        }

        return list;
    }

    @Override
    public boolean existById(String id) {
        if(studentMap.containsKey(id)) {
            return true;
        }

        return false;
    }
}
