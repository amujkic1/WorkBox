package com.example.hr.clrunner;

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
        Record record1 = new Record(123456789, new Date(), "+38761234567", "Adresa 1", "email1@example.com", new Date(), "Active", new Time(System.currentTimeMillis()));
        Record record2 = new Record(987654321, new Date(), "+38761234568", "Adresa 2", "email2@example.com", new Date(), "Inactive", new Time(System.currentTimeMillis()));
        Record record3 = new Record(456789123, new Date(), "+38761234569", "Adresa 3", "email3@example.com", new Date(), "Pending", new Time(System.currentTimeMillis()));

        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);

        log.info("Records uneseni u bazu!");

    }

}
