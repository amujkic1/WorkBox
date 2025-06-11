package com.example.auth.controller;

import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public Optional<User> getByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }
}