package com.example.demo.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class RecordRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RecordRepositoryCommandLineRunner.class);

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void run(String... args) throws Exception {

    }

}
