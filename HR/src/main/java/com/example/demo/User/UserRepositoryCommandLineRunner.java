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
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;  // Dodajemo repozitorij za Record

    @Override
    public void run(String... args) {

        log.info("-------------------------------");
        log.info("Adding Harry as Admin");
        log.info("-------------------------------");

        Optional<Record> recordOptional = recordRepository.findById(2L);
        Record record;
        if (recordOptional.isPresent()) {
            record = recordOptional.get();
        } else {
            record = new Record(987654321, null, "789-012", "King's Cross", "harry@example.com", null, "Active", null);
            recordRepository.save(record);
        }

        // Kreiramo korisnika i povezujemo s Record objektom
        User harry = new User("Harry", "Potter", "Admin", "harry_admin", "password123", record);
        userRepository.save(harry);

        log.info("Inserted Harry: " + harry);

        log.info("-------------------------------");
        log.info("Finding all users");
        log.info("-------------------------------");
        for (User user : userRepository.findAll()) {
            log.info(user.toString());
        }
    }
}
