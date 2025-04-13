package com.example.hr.controller;

import com.example.hr.controller.ApplicationController;
import com.example.hr.dto.ApplicationDTO;
import com.example.hr.model.Application;
import com.example.hr.model.Opening;
import com.example.hr.repository.ApplicationRepository;
import com.example.hr.repository.OpeningRepository;
import com.example.hr.service.ApplicationService;
import com.example.hr.assembler.ApplicationModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ApplicationController.class)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ApplicationRepository applicationRepository;

    @SuppressWarnings("removal")
    @MockBean
    private OpeningRepository openingRepository;

    @SuppressWarnings("removal")
    @MockBean
    private ApplicationService applicationService;

    @SuppressWarnings("removal")
    @MockBean
    private ApplicationModelAssembler applicationModelAssembler;

    @SuppressWarnings("removal")
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostApplication_invalidEmail_returns400ValidationError() throws Exception {
        // Moramo mockati opening zbog validacije
        when(openingRepository.findById(any())).thenReturn(Optional.of(new Opening()));

        ApplicationDTO dto = new ApplicationDTO();
        dto.setDate(new Date());
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setEmail("invalid-email");
        dto.setContactNumber("+38761123456");
        dto.setDocumentationLink("https://example.com");
        dto.setStatus("Pending");
        dto.setPoints(85.0);
        dto.setOpeningId(1);

        mockMvc.perform(post("/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation"))
                .andExpect(jsonPath("$.messages.email").value("Invalid email format"));
    }



}








