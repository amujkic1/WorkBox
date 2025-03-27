package com.example.demo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
//@Order(1)
public class UserRepositoryCommandLineRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(1,"Emy", "Pots");
        User user2 = new User(2,"Tony", "Lyon");
        User user3 = new User(3,"ALice", "McJustom");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);


        userRepository.save(user1);
        userRepository.save(user2);

        log.info("Korisnici dodani u bazu!");
    }
}
