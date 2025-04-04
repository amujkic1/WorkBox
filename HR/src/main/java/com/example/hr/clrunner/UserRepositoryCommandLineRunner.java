package com.example.hr.clrunner;

import com.example.hr.model.Opening;
import com.example.hr.model.User;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.model.Record;
import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Order(3)
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private OpeningRepository openingRepository;

    @Override
    public void run(String... args) {
        List<Record> records = recordRepository.findAll();

        if (records.size() < 3) {
            log.error("Nema dovoljno Record unosa u bazi!");
            return;
        }

        User user1 = new User("Emina", "Peljto", "Admin", "Emina", "password1", records.get(0));
        User user2 = new User("Hasan", "Brčaninović", "Admin", "Hasan", "password2", records.get(1));
        User user3 = new User("Ajna", "Mujkić", "Admin", "Ajna123", "password3", records.get(2));

        // Prvo spremite korisnike, jer oni nemaju reference na "Opening" još
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // Postavite datum za start i end
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); // Postavi endDate u budućnost
        Date futureDate = calendar.getTime();
        Date currentDate = new Date();

        // Spremite Openings entitete
        Opening opening1 = new Opening("Software Engineer", "Development job", "Bachelor's degree", "Flexible hours", currentDate, futureDate, "Open", user1);
        Opening opening2 = new Opening("Data Analyst", "Analyze data trends", "Experience with SQL", "Remote work", currentDate, futureDate, "Open", user2);

        // Spremite Opening entitete
        openingRepository.save(opening1);
        openingRepository.save(opening2);

        // Povežite korisnike sa Openings
        user1.addOpening(opening1);
        user1.addOpening(opening2);
        user2.addOpening(opening1);

        // Spremite korisnike ponovo nakon što su povezani sa Opening entitetima
        userRepository.save(user1);
        userRepository.save(user2);

        log.info("Users i Openings dodani u bazu!");
    }
}
