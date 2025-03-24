package com.example.demo.User;

import com.example.demo.Record.Record;
import com.example.demo.Record.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserEntityManagerCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserEntityManagerCommandLineRunner.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RecordRepository recordRepository;  // Dodali repo za Record

    @Override
    public void run(String... args) {

        log.info("-------------------------------");
        log.info("Adding Tom as Admin");
        log.info("-------------------------------");

        Optional<Record> recordOptional = recordRepository.findById(1L);
        Record record;
        if (recordOptional.isPresent()) {
            record = recordOptional.get();
        } else {
            record = new Record(123456789, null, "123-456", "Main St", "tom@example.com", null, "Active", null);
            recordRepository.save(record);
        }

        User tom = new User("Tom", "Smith", "Admin", "tom_admin", "password123", record);
        userService.insert(tom);
        log.info("Inserted Tom: " + tom);

        log.info("-------------------------------");
        log.info("Finding user with id 1");
        log.info("-------------------------------");
        User user = userService.find(1L);
        log.info(user != null ? user.toString() : "User not found");

        log.info("-------------------------------");
        log.info("Finding all users");
        log.info("-------------------------------");
        log.info(userService.findAll().toString());
    }
}
