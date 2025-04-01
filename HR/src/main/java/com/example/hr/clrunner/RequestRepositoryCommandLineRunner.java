package com.example.hr.clrunner;

import com.example.hr.model.Record;
import com.example.hr.model.Request;
import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Order(2)
public class RequestRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RequestRepositoryCommandLineRunner.class);

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Record> records = recordRepository.findAll();

        Request request1 = new Request("Leave Request", "Tražim odmor od 10 dana.", new Date(), "Pending", records.get(0));
        Request request2 = new Request("Salary Increase", "Molim povećanje plate zbog povećanog obima posla.", new Date(), "Approved", records.get(1));
        Request request3 = new Request("Equipment Request", "Potrebna nova tastatura za rad.", new Date(), "Pending", records.get(2));
        Request request4 = new Request("Remote Work", "Želim raditi od kuće tri dana sedmično.", new Date(), "Rejected", records.get(0));

        requestRepository.save(request1);
        requestRepository.save(request2);
        requestRepository.save(request3);
        requestRepository.save(request4);

        log.info("Requests uneseni u bazu!");

    }
}
