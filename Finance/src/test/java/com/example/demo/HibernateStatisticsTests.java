package com.example.demo;

import com.example.demo.models.CheckInRecord;
import com.example.demo.models.User;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class HibernateStatisticsTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheckInRecordRepository checkInRecordRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    void DBinitalize(){

        User user = new User("TestIme1", "TestPrezime1", 1.5);
        userRepository.save(user);

        CheckInRecord checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);


        user = new User("TestIme2", "TestPrezime", 1.5);
        userRepository.save(user);

        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);

        user = new User("TestIme3", "TestPrezime", 1.5);
        userRepository.save(user);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);

        user = new User("TestIme4", "TestPrezime", 1.5);
        userRepository.save(user);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);

        user = new User("TestIme5", "TestPrezime", 1.5);
        userRepository.save(user);
        checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordRepository.save(checkInRecord);

        System.out.println("DB initialized");
    }

    @BeforeEach
    void setUp(){
        assertNotNull(entityManager, "TestEntityManager je null");
        EntityManager em = entityManager.getEntityManager();

        Session session = em.unwrap(Session.class);
        statistics = session.getSessionFactory().getStatistics();
        statistics.clear();
    }


    @Test
    void testLazyLoadingWithoutNPlusOneProblem() {
        List<User> permissions = userRepository.findAll();
        long queryCount = statistics.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);
        assertTrue(queryCount <= 2, "N+1 problem detektovan! Previše upita je izvršeno.");
    }

    @Test
    void testLazyLoadingWithoutNPlusOneProblem2() {
        List<CheckInRecord> permissions = checkInRecordRepository.findAll();
        long queryCount = statistics.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);
        assertTrue(queryCount <= 2, "N+1 problem detektovan! Previše upita je izvršeno.");
    }

}
