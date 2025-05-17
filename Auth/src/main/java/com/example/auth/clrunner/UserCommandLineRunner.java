package com.example.auth.clrunner;

import com.example.auth.model.Role;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        Role role = Role.USER;

        User user1 = new User("Emina", "Peljto", "epeljto1", "password123", role);
        userRepository.save(user1);

        System.out.println("User added successfully");
    }
}
