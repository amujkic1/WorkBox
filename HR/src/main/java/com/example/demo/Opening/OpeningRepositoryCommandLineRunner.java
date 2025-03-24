package com.example.demo.Opening;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;

public class OpeningRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OpeningRepositoryCommandLineRunner.class);

    @Autowired
    private OpeningRepository openingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userOptional = userRepository.findById(1L);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        else{
            user = new User("John", "Doe", "Admin", "john.doe", "password123");
            userRepository.save(user);
        }

        Opening opening = new Opening("Software Engineer", "Full-time", user);
        openingRepository.save(opening);

        log.info("Inserted Opening: " + opening);

        log.info("-------------------------------");
        log.info("Finding all job openings");
        log.info("-------------------------------");
        for (Opening o : openingRepository.findAll()) {
            log.info(o.toString());
        }
    }

}
