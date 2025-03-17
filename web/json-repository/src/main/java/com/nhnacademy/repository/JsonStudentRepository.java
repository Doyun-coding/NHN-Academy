package com.nhnacademy.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.student.Student;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JsonStudentRepository implements StudentRepository{
    private final ObjectMapper objectMapper;
    private static final String JSON_FILE_PATH="/Users/kimdoyun/Desktop/NHNAcademy/student/src/main/resources/student.json";

    private File file;

    public JsonStudentRepository() {
        objectMapper = new ObjectMapper();

        //LocalDatetime json 직열화/역직렬화 가능하도록 설정
        objectMapper.registerModule(new JavaTimeModule());

        file = new File(JSON_FILE_PATH);

        //todo JSON_FILE_PATH 경로에 json 파일이 존재하면 삭제 합니다.
        if(file.exists()) {
            log.debug("file is exists!");
            file.delete();
        }

    }

    private synchronized List<Student> readJsonFile() {
        List<Student> students = new ArrayList<>();

        //todo json 파일이 존재하지 않다면 비어있는 List<Student> 리턴
        if(!file.exists()) {
            return students;
        }

        file = new File(JSON_FILE_PATH);

        try(FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            students = objectMapper.readValue(bufferedReader, new TypeReference<List<Student>>() {});

            return students;
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeJsonFile(List<Student> studentList) {
        if(!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        try(FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            objectMapper.writeValue(bufferedWriter, studentList);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Student student) {
        List<Student> students = readJsonFile();
        students.add(student);
        writeJsonFile(students);
    }

    @Override
    public void update(Student student) {
        List<Student> students = readJsonFile();

        for(int i = 0; i < students.size(); i++) {
            if(students.get(i).getId().equals(student.getId())) {
                students.remove(i);
                break;
            }
        }

        students.add(student);
        writeJsonFile(students);
    }

    @Override
    public void deleteById(String id) {
        List<Student> students = readJsonFile();

        for(int i = 0; i < students.size(); i++) {
            if(id.equals(students.get(i).getId())) {
                students.remove(i);
                break;
            }
        }

        writeJsonFile(students);
    }

    @Override
    public Student getStudentById(String id) {
        List<Student> students = readJsonFile();
        Student student = null;

        for(int i = 0; i < students.size(); i++) {
            if(id.equals(students.get(i).getId())) {
                student = students.get(i);
                break;
            }
        }

        return student;
    }

    @Override
    public List<Student> getStudents() {
        return readJsonFile();
    }

    @Override
    public boolean existById(String id) {
        List<Student> students = readJsonFile();

        for(int i = 0; i < students.size(); i++) {
            if(id.equals(students.get(i).getId())) {
                return true;
            }
        }

        return false;
    }
}
