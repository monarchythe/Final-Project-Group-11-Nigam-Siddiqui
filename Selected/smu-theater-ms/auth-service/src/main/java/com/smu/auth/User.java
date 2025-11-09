package com.smu.auth;

public class User {
    public String username;
    public String password;

    // 🧩 Jackson needs this default constructor
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
