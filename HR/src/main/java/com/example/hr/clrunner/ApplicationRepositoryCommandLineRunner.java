package com.example.hr.clrunner;

import com.example.hr.model.Opening;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.model.Application;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.service.HibernateStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Order(4)
public class ApplicationRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRepositoryCommandLineRunner.class);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OpeningRepository openingRepository;

    @Autowired
    private HibernateStatisticsService statisticsService;

    @Override
    public void run(String... args) throws Exception {
        List<Opening> openings = openingRepository.findAll();

        if (openings.size() < 3) {
            log.warn("Not enough openings to assign applications. Please ensure at least 3 openings exist.");
            return;
        }

        Application app1 = new Application(
                new Date(),
                "John", "Doe",
                "john.doe@example.com",
                "+38761111222",
                "https://example.com/cv/johndoe",
                "Pending",
                88.5,
                openings.get(0)
        );

        Application app2 = new Application(
                new Date(),
                "Emily", "Clark",
                "emily.clark@example.com",
                "+38762123456",
                "https://example.com/cv/emilyclark",
                "Accepted",
                91.2,
                openings.get(0)
        );

        Application app3 = new Application(
                new Date(),
                "Michael", "Brown",
                "michael.brown@example.com",
                "+38763123456",
                "https://example.com/cv/michaelbrown",
                "Rejected",
                72.0,
                openings.get(1)
        );

        Application app4 = new Application(
                new Date(),
                "Sara", "Peterson",
                "sara.peterson@example.com",
                "+38764123456",
                "https://example.com/cv/sarapeterson",
                "Pending",
                85.0,
                openings.get(1)
        );

        Application app5 = new Application(
                new Date(),
                "David", "Wilson",
                "david.wilson@example.com",
                "+38765123456",
                "https://example.com/cv/davidwilson",
                "Pending",
                95.3,
                openings.get(2)
        );

        Application app6 = new Application(
                new Date(),
                "Laura", "Smith",
                "laura.smith@example.com",
                "+38766123456",
                "https://example.com/cv/laurasmith",
                "Pending",
                70.8,
                openings.get(2)
        );

        applicationRepository.saveAll(List.of(app1, app2, app3, app4, app5, app6));

        log.info("6 demo Applications successfully inserted into the database.");
    }


/*    @Override
    public void run(String... args) throws Exception {

        List<Opening> openings = openingRepository.findAll();

        Opening opening = openings.get(0);

        Application app1 = new Application(new Date(), "Mujo", "Mujić", "mujo.mujic@gmail.com", "+061123456", "https://docs.com/cv1", "Pending", 85.0, opening);
        Application app2 = new Application(new Date(), "Haso", "Hasić", "haso.hasic@gmail.com", "+062456789", "https://docs.com/cv2", "Accepted", 92.5, opening);
        Application app3 = new Application(new Date(), "Fata", "Fatić", "fata.fatic@gmail.com", "+063789012", "https://docs.com/cv3", "Rejected", 74.0, opening);

        applicationRepository.save(app1);
        applicationRepository.save(app2);
        applicationRepository.save(app3);

        log.info("Applications unesene u bazu!");
        //statisticsService.logStatistics();

    }*/
}
