package com.nhnacademy.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Student {
    private String id;
    private String name;
    private Gender gender;
    private int age;
    private LocalDateTime createAt;

    @JsonCreator
    public Student(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("gender") Gender gender,
            @JsonProperty("age") int age
    ) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createAt = LocalDateTime.now();
    }

//    public Student(String id, String name, Gender gender, int age) {
//        this.id = id;
//        this.name = name;
//        this.gender = gender;
//        this.age = age;
//        createAt = LocalDateTime.now();
//    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }
}
