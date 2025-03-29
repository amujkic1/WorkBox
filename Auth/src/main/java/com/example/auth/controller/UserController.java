package com.example.auth.controller;

import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/user")
    Optional<User> getByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody String firstName, String lastName, String username, String password) {
        // userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    // @PutMapping("/user")


}
