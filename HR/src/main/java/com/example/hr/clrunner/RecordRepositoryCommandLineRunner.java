/*package com.example.hr.clrunner;

import com.example.hr.model.Record;
import com.example.hr.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;

@Component
@Order(1)
public class RecordRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RecordRepositoryCommandLineRunner.class);

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void run(String... args) throws Exception {

        Date now = new Date();

        Record record1 = Record.builder()
                .jmbg(1234567890123L)
                .birthDate(now)
                .contactNumber("+38761234567")
                .address("Adresa 1")
                .email("email1@example.com")
                .employmentDate(now)
                .status("Active")
                .workingHours(8)
                .build();

        Record record2 = Record.builder()
                .jmbg(9876543210123L)
                .birthDate(now)
                .contactNumber("+38761234568")
                .address("Adresa 2")
                .email("email2@example.com")
                .employmentDate(now)
                .status("Inactive")
                .workingHours(8)
                .build();

        Record record3 = Record.builder()
                .jmbg(4567891230123L)
                .birthDate(now)
                .contactNumber("+38761234569")
                .address("Adresa 3")
                .email("email3@example.com")
                .employmentDate(now)
                .status("Pending")
                .workingHours(6)
                .build();
        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);

        log.info("Records uneseni u bazu!");

    }

}*/