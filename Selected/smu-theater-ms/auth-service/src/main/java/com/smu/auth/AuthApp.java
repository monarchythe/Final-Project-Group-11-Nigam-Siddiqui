package com.smu.auth;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.*;

public class AuthApp {

    private static final Map<String, User> USERS = new HashMap<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create(cfg -> cfg.http.defaultContentType = "application/json")
                .start(8082);

        app.post("/auth/register", AuthApp::register);
        app.post("/auth/login", AuthApp::login);
    }

    private static void register(Context ctx) {
        User u = ctx.bodyAsClass(User.class);
        if (USERS.containsKey(u.username)) {
            ctx.status(400).result("{\"error\":\"User already exists\"}");
            return;
        }
        USERS.put(u.username, u);
        ctx.status(201).result("{\"message\":\"Registered successfully\"}");
    }

    private static void login(Context ctx) {
        User u = ctx.bodyAsClass(User.class);
        User stored = USERS.get(u.username);

        if (stored == null || !stored.password.equals(u.password)) {
            ctx.status(401).result("{\"error\":\"Invalid credentials\"}");
            return;
        }

        String token = Base64.getEncoder().encodeToString((u.username + ":token").getBytes());
        ctx.status(200).result("{\"token\":\"" + token + "\"}");
    }
}
