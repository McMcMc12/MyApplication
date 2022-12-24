package com.example.myapplication;

import com.google.android.gms.common.internal.Objects;

public class User {
    public String name, email, student, password;

    public User(String name, String email, String student, String password) {
        this.name = name;
        this.email = email;
        this.student = student;
        this.password = password;
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

    public String getpassword() {
        return password;
    }
}
