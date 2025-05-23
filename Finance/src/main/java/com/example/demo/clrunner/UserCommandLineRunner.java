package com.example.demo.clrunner;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
@Order(1)
public class UserCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    //private UserService userService;

    @Override
    public void run(String... args) throws Exception {


        User user = new User("Test ime","Test prezime",1.5);
        userRepository.save(user);

    }

}
