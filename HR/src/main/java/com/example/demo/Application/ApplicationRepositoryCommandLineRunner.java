package com.example.demo.Application;

import com.example.demo.Request.RequestRepositoryCommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRepositoryCommandLineRunner.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
