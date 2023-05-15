package com.example.myapplication;

public class User {
    public String name, email, student;

    public User(String name, String email, String student) {
        this.name = name;
        this.email = email;
        this.student = student;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStudent() {
        return student;
    }

}
