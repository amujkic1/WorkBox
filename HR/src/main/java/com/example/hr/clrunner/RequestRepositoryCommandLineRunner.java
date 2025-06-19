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
            log.warn("No users found in the database!");
            return;
        } else {
            log.info("Found users:");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                log.info("User {}: id={}, name={} {}, username={}, email={}",
                        i + 1, user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
            }
        }

        // Representative demo requests
        Request req1 = Request.builder()
                .type("Annual Leave")
                .text("I would like to take annual leave from July 1st to July 10th.")
                .date(new Date())
                .status("Pending")
                .user(users.get(0))
                .build();

        Request req2 = Request.builder()
                .type("Remote Work")
                .text("Requesting remote work for next Monday and Tuesday due to personal reasons.")
                .date(new Date())
                .status("Approved")
                .user(users.get(1))
                .build();

        Request req3 = Request.builder()
                .type("Salary Raise")
                .text("Based on my performance and additional responsibilities, I request a salary raise.")
                .date(new Date())
                .status("Pending")
                .user(users.get(2))
                .build();

        Request req4 = Request.builder()
                .type("New Equipment")
                .text("Please provide a new laptop as the current one is malfunctioning.")
                .date(new Date())
                .status("Rejected")
                .user(users.get(0))
                .build();

        Request req5 = Request.builder()
                .type("Training Request")
                .text("I would like to attend the upcoming AWS cloud training to improve my skills.")
                .date(new Date())
                .status("Approved")
                .user(users.get(1))
                .build();

        Request req6 = Request.builder()
                .type("Schedule Adjustment")
                .text("I am requesting a shift adjustment for next week due to family obligations.")
                .date(new Date())
                .status("Pending")
                .user(users.get(2))
                .build();

        // Save all requests
        requestRepository.saveAll(List.of(req1, req2, req3, req4, req5, req6));
        log.info("6 demo Requests successfully inserted into the database.");
    }

    /*public void run(String... args) throws Exception {
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

    }*/
}
