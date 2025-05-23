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

        Opening opening = openings.get(0);

        Application app1 = new Application(new Date(), "Mujo", "Mujić", "mujo.mujic@gmail.com", "+061123456", "https://docs.com/cv1", "Pending", 85.0, opening);
        Application app2 = new Application(new Date(), "Haso", "Hasić", "haso.hasic@gmail.com", "+062456789", "https://docs.com/cv2", "Accepted", 92.5, opening);
        Application app3 = new Application(new Date(), "Fata", "Fatić", "fata.fatic@gmail.com", "+063789012", "https://docs.com/cv3", "Rejected", 74.0, opening);

        applicationRepository.save(app1);
        applicationRepository.save(app2);
        applicationRepository.save(app3);

        log.info("Applications unesene u bazu!");
        //statisticsService.logStatistics();

    }
}
