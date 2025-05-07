package com.example.business;

import com.example.business.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjectTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void contextLoads() {
    }

}
