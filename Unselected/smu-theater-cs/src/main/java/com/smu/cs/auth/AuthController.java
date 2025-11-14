package com.smu.cs.auth;

import com.smu.cs.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final Map<String, User> users = new HashMap<>();

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        users.put(user.username, user);
        return Map.of("message", "Registered successfully!");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User stored = users.get(user.username);

        if (stored == null || !stored.password.equals(user.password)) {
            return Map.of("error", "Invalid credentials");
        }

        String token = user.username + "-token"; // simple token
        return Map.of("token", token);
    }
}
