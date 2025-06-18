package com.example.hr.clrunner;

import com.example.hr.model.Opening;
import com.example.hr.model.Record;
import com.example.hr.model.User;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.repository.RecordRepository;
import com.example.hr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(1)
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private OpeningRepository openingRepository;

    @Override
    public void run(String... args) throws Exception {
        Date now = new Date();

        // Kreiranje korisnika
        User user1 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Emina")
                .lastName("Peljto")
                .role("Admin")
                .username("Emina")
                .password("password1")
                .email("email1@example.com")
                .build();

        User user2 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Hasan")
                .lastName("Brčaninović")
                .role("Admin")
                .username("Hasan")
                .password("password2")
                .email("email2@example.com")
                .build();

        User user3 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Ajna")
                .lastName("Mujkić")
                .role("Admin")
                .username("Ajna123")
                .password("password3")
                .email("email3@example.com")
                .build();

        // Kreiranje Record objekata povezani s korisnicima
        Record record1 = Record.builder()
                .jmbg(1234567890123L)
                .birthDate(now)
                .contactNumber("+38761234567")
                .address("Adresa 1")
                .email("email1@example.com")
                .employmentDate(now)
                .status("ACTIVE")
                .workingHours(8)
                .build();

        Record record2 = Record.builder()
                .jmbg(9876543210123L)
                .birthDate(now)
                .contactNumber("+38761234568")
                .address("Adresa 2")
                .email("email2@example.com")
                .employmentDate(now)
                .status("ANNUAL_LEAVE")
                .workingHours(8)
                .build();

        Record record3 = Record.builder()
                .jmbg(4567891230123L)
                .birthDate(now)
                .contactNumber("+38761234569")
                .address("Adresa 3")
                .email("email3@example.com")
                .employmentDate(now)
                .status("SICK_LEAVE")
                .workingHours(6)
                .build();

        // Povezivanje korisnika i njihovih zapisa
        user1.setRecord(record1);
        record1.setUser(user1);

        user2.setRecord(record2);
        record2.setUser(user2);

        user3.setRecord(record3);
        record3.setUser(user3);

        // Spasi korisnike (cascade ALL će spasiti i Record zbog veze)
        userRepository.saveAll(Arrays.asList(user1, user2, user3));
        // Nije potrebno posebno čuvati Record jer je Cascade.ALL uključen

        // Kreiranje Opening objekata
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date futureDate = calendar.getTime();

        Opening opening1 = Opening.builder()
                .openingName("Software Engineer")
                .description("Development job")
                .conditions("Bachelor's degree")
                .benefits("Flexible hours")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user1)  // vlasnik otvaranja
                .users(new ArrayList<>())
                .build();

        Opening opening2 = Opening.builder()
                .openingName("Data Analyst")
                .description("Analyze data trends")
                .conditions("Experience with SQL")
                .benefits("Remote work")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user2)
                .users(new ArrayList<>())
                .build();

        // Spasi opening-e
        openingRepository.saveAll(Arrays.asList(opening1, opening2));

        // Poveži opening-e sa korisnicima (many-to-many)
        user1.addOpening(opening1);
        user1.addOpening(opening2);
        user2.addOpening(opening1);

        // Spasi korisnike opet da se ažuriraju veze u bazi
        userRepository.saveAll(Arrays.asList(user1, user2));

        log.info("Users, Records i Openings uspješno dodani u bazu!");
    }
}


/*package com.example.hr.clrunner;

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

import java.sql.Time;
import java.util.*;

@Component
@Order(2)
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

        Date now = new Date();
        Time currentTime = new Time(System.currentTimeMillis());

        User user1 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Emina")
                .lastName("Peljto")
                .role("Admin")
                .username("Emina")
                .password("password1")
                .email("email1@example.com")
                .build();

        User user2 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Hasan")
                .lastName("Brčaninović")
                .role("Admin")
                .username("Hasan")
                .password("password2")
                .email("email2@example.com")
                .build();

        User user3 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Ajna")
                .lastName("Mujkić")
                .role("Admin")
                .username("Ajna123")
                .password("password3")
                .email("email3@example.com")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date futureDate = calendar.getTime();
        Date currentDate = new Date();

        Opening opening1 = Opening.builder()
                .openingName("Software Engineer")
                .description("Development job")
                .conditions("Bachelor's degree")
                .benefits("Flexible hours")
                .startDate(currentDate)
                .endDate(futureDate)
                .result("Open")
                .user(user1)
                .users(new ArrayList<>())
                .build();

        Opening opening2 = Opening.builder()
                .openingName("Data Analyst")
                .description("Analyze data trends")
                .conditions("Experience with SQL")
                .benefits("Remote work")
                .startDate(currentDate)
                .endDate(futureDate)
                .result("Open")
                .user(user2)
                .users(new ArrayList<>())
                .build();

        openingRepository.save(opening1);
        openingRepository.save(opening2);

        user1.addOpening(opening1);
        user1.addOpening(opening2);
        user2.addOpening(opening1);

        userRepository.save(user1);
        userRepository.save(user2);

        log.info("Users i Openings dodani u bazu!");
    }
}
*/