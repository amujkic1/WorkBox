package com.example.demo.Request;

import com.example.demo.Record.Record;
import com.example.demo.Record.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class RequestRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RequestRepositoryCommandLineRunner.class);

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RecordRepository recordRepository; // Dodaj repo za Record

    @Override
    public void run(String... args) throws Exception {
        // Prvo pokušaj dohvatiti postojeći Record
        Optional<Record> optionalRecord = recordRepository.findById(1L); // Promijeni ID po potrebi

        if (optionalRecord.isPresent()) {
            Record record = optionalRecord.get();

            // Kreiranje Request objekta sa povezanim Record objektom
            Request req = new Request("vacation", "I would like to request a vacation in July",
                    new Date(), "pending", record);

            requestRepository.save(req);

            log.info("-------------------------------");
            log.info("New request saved: " + req);
            log.info("-------------------------------");

            log.info("Finding all requests...");
            for (Request request : requestRepository.findAll()) {
                log.info(request.toString());
            }
        } else {
            log.error("No Record found with ID 1. Cannot create Request.");
        }
    }
}
