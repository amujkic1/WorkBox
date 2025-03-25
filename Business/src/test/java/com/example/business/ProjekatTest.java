package com.example.business;

import com.example.business.model.Projekat;
import com.example.business.repository.ProjekatRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjekatTest {
    @Autowired
    private ProjekatRepository projekatRepository;

    @Test
    void contextLoads() {
    }

}
