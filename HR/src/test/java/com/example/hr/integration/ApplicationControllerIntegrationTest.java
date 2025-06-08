package com.example.hr.integration;

import com.example.hr.model.Application;
import com.example.hr.model.Opening;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.repository.OpeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OpeningRepository openingRepository;

    @BeforeEach
    void setup() {
        // Očistimo bazu da test ne padne zbog duplikata
        //applicationRepository.deleteAll();
        //openingRepository.deleteAll();

        // Kreiramo i spremamo Opening
        Opening opening = new Opening();
        //opening.setId(100);
        openingRepository.save(opening);

        // Kreiramo i spremamo Application
        Application app = new Application();
        //app.setId(100);
        app.setFirstName("Mujo");
        app.setLastName("Mujić");
        app.setEmail("mujo.mujic@gmail.com");
        app.setDate(new Date());
        app.setContactNumber("+38761123456");
        app.setDocumentationLink("https://example.com");
        app.setStatus("Pending");
        app.setPoints(85.0);
        app.setOpening(opening);

        applicationRepository.save(app);
    }

    /* @Test
    void testGetApplicationById() throws Exception {
        Integer applicationId = 100;

        ResultActions result = mockMvc.perform(get("/applications/{id}", applicationId));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(applicationId))
                .andExpect(jsonPath("$.email").value("mujo.mujic@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Mujo"))
                .andExpect(jsonPath("$.lastName").value("Mujić"));
    } */
}

//https://medium.com/@mbanaee61/api-testing-in-spring-boot-2a6d69e5c3ce