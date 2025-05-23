package com.example.hr.clrunner;

import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.UserRepository;
import com.example.hr.repository.OpeningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpeningRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OpeningRepositoryCommandLineRunner.class);

    @Autowired
    private OpeningRepository openingRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
