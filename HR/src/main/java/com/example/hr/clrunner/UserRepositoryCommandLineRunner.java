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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date futureDate = calendar.getTime();

        // Users
        User user1 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Emily")
                .lastName("Johnson")
                .role("Admin")
                .username("emily")
                .password("admin123")
                .email("emily.johnson@example.com")
                .build();

        User user2 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Michael")
                .lastName("Smith")
                .role("User")
                .username("michael")
                .password("user123")
                .email("michael.smith@example.com")
                .build();

        User user3 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Sophia")
                .lastName("Brown")
                .role("User")
                .username("sophia")
                .password("user456")
                .email("sophia.brown@example.com")
                .build();

        User user4 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Daniel")
                .lastName("Taylor")
                .role("User")
                .username("daniel")
                .password("user789")
                .email("daniel.taylor@example.com")
                .build();

        User user5 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Olivia")
                .lastName("Williams")
                .role("User")
                .username("olivia")
                .password("passolivia")
                .email("olivia.williams@example.com")
                .build();

        User user6 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Liam")
                .lastName("Anderson")
                .role("User")
                .username("liam")
                .password("passliam")
                .email("liam.anderson@example.com")
                .build();

        User user7 = User.builder()
                .uuid(UUID.randomUUID())
                .firstName("Chloe")
                .lastName("Martinez")
                .role("User")
                .username("chloe")
                .password("passchloe")
                .email("chloe.martinez@example.com")
                .build();


        // Records
        Record record1 = Record.builder()
                .jmbg(1112223334445L)
                .birthDate(new GregorianCalendar(1990, Calendar.JANUARY, 5).getTime())
                .contactNumber("+38761111222")
                .address("Green Street 12, Sarajevo")
                .email("emily.johnson@example.com")
                .employmentDate(now)
                .status("ACTIVE")
                .workingHours(8)
                .build();

        Record record2 = Record.builder()
                .jmbg(2223334445556L)
                .birthDate(new GregorianCalendar(1985, Calendar.MAY, 20).getTime())
                .contactNumber("+38761111223")
                .address("Blue Avenue 3, Mostar")
                .email("michael.smith@example.com")
                .employmentDate(now)
                .status("INACTIVE")
                .workingHours(4)
                .build();

        Record record3 = Record.builder()
                .jmbg(3334445556667L)
                .birthDate(new GregorianCalendar(1995, Calendar.MARCH, 10).getTime())
                .contactNumber("+38761111224")
                .address("Red Square 10, Tuzla")
                .email("sophia.brown@example.com")
                .employmentDate(now)
                .status("ANNUAL_LEAVE")
                .workingHours(6)
                .build();

        Record record4 = Record.builder()
                .jmbg(4445556667778L)
                .birthDate(new GregorianCalendar(1992, Calendar.JUNE, 15).getTime())
                .contactNumber("+38761111225")
                .address("Sunset Blvd 22, Zenica")
                .email("daniel.taylor@example.com")
                .employmentDate(now)
                .status("SICK_LEAVE")
                .workingHours(5)
                .build();

        Record record5 = Record.builder()
                .jmbg(5556667778889L)
                .birthDate(new GregorianCalendar(1993, Calendar.NOVEMBER, 23).getTime())
                .contactNumber("+38761111226")
                .address("Riverbank Street 18, Banja Luka")
                .email("olivia.williams@example.com")
                .employmentDate(now)
                .status("ACTIVE")
                .workingHours(7)
                .build();

        Record record6 = Record.builder()
                .jmbg(6667778889990L)
                .birthDate(new GregorianCalendar(1989, Calendar.APRIL, 2).getTime())
                .contactNumber("+38761111227")
                .address("Hillview Ave 7, Travnik")
                .email("liam.anderson@example.com")
                .employmentDate(now)
                .status("INACTIVE")
                .workingHours(4)
                .build();

        Record record7 = Record.builder()
                .jmbg(7778889990001L)
                .birthDate(new GregorianCalendar(1996, Calendar.AUGUST, 30).getTime())
                .contactNumber("+38761111228")
                .address("Lakeside Road 5, Bihać")
                .email("chloe.martinez@example.com")
                .employmentDate(now)
                .status("ACTIVE")
                .workingHours(9)
                .build();


        // Link users with records
        user1.setRecord(record1);
        record1.setUser(user1);

        user2.setRecord(record2);
        record2.setUser(user2);

        user3.setRecord(record3);
        record3.setUser(user3);

        user4.setRecord(record4);
        record4.setUser(user4);

        user5.setRecord(record5);
        record5.setUser(user5);

        user6.setRecord(record6);
        record6.setUser(user6);

        user7.setRecord(record7);
        record7.setUser(user7);

        // Save users with records
        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6, user7));

        // Openings
        Opening opening1 = Opening.builder()
                .openingName("Backend Developer")
                .description("Responsible for server-side application logic")
                .conditions("3+ years of experience with Java")
                .benefits("Healthcare, Flexible hours")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user1)
                .users(new ArrayList<>())
                .build();

        Opening opening2 = Opening.builder()
                .openingName("UX Designer")
                .description("Design intuitive and user-friendly interfaces")
                .conditions("Knowledge of Figma and UX principles")
                .benefits("Remote work, Education budget")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user2)
                .users(new ArrayList<>())
                .build();

        Opening opening3 = Opening.builder()
                .openingName("QA Tester")
                .description("Ensure quality of software through testing")
                .conditions("Attention to detail, experience with automation tools")
                .benefits("Bonuses, Paid time off")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user1)
                .users(new ArrayList<>())
                .build();

        Opening opening4 = Opening.builder()
                .openingName("DevOps Engineer")
                .description("Maintain and improve CI/CD pipelines")
                .conditions("Experience with Docker, Kubernetes")
                .benefits("Gym membership, Remote work")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user3)
                .users(new ArrayList<>())
                .build();

        Opening opening5 = Opening.builder()
                .openingName("Frontend Developer")
                .description("Build modern web interfaces with React")
                .conditions("Strong HTML/CSS/JS knowledge")
                .benefits("Flexible schedule, Paid time off")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user5)
                .users(new ArrayList<>())
                .build();

        Opening opening6 = Opening.builder()
                .openingName("Project Manager")
                .description("Coordinate agile teams and timelines")
                .conditions("PMP certificate preferred")
                .benefits("Company car, Annual bonus")
                .startDate(now)
                .endDate(futureDate)
                .result("Open")
                .user(user6)
                .users(new ArrayList<>())
                .build();

        openingRepository.saveAll(Arrays.asList(opening1, opening2, opening3, opening4, opening5, opening6));

        // Assign openings to users
        user1.addOpening(opening1);
        user1.addOpening(opening3);

        user2.addOpening(opening1);
        user3.addOpening(opening2);
        user4.addOpening(opening2);
        user4.addOpening(opening3);
        user3.addOpening(opening4);
        user5.addOpening(opening5);
        user6.addOpening(opening6);
        user7.addOpening(opening5);
        user7.addOpening(opening6);

        // Save users again with openings
        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6, user7));

        log.info("Sample users, records, and openings inserted successfully!");
    }

    /*
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
    }*/
}