package com.example.hr.clrunner;

import com.example.hr.model.Request;
import com.example.hr.model.User;
import com.example.hr.repository.RequestRepository;
import com.example.hr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Order(3)
public class RequestRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RequestRepositoryCommandLineRunner.class);

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("Nema korisnika u bazi!");
        } else {
            log.info("Pronađeni korisnici:");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                log.info("Korisnik {}: id={}, ime={}, username={}, email={}", i, user.getId(), user.getFirstName(), user.getUsername(), user.getEmail());
            }
        }


        Request request1 = Request.builder()
                .type("Leave")
                .text("Tražim odmor od 10 dana.")
                .date(new Date())
                .status("Pending")
                .user(users.get(0))
                .build();

        Request request2 = Request.builder()
                .type("Salary Increase")
                .text("Molim povećanje plate zbog povećanog obima posla.")
                .date(new Date())
                .status("Approved")
                .user(users.get(1))
                .build();

        Request request3 = Request.builder()
                .type("Equipment Request")
                .text("Potrebna nova tastatura za rad.")
                .date(new Date())
                .status("Pending")
                .user(users.get(2))
                .build();

        Request request4 = Request.builder()
                .type("Remote Work")
                .text("Želim raditi od kuće tri dana sedmično.")
                .date(new Date())
                .status("Rejected")
                .user(users.get(0))
                .build();

        requestRepository.save(request1);
        requestRepository.save(request2);
        requestRepository.save(request3);
        requestRepository.save(request4);

        log.info("Requests uneseni u bazu!");

    }
}
