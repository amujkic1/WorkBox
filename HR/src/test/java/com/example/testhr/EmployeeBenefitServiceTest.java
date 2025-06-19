package com.example.testhr;

import com.example.hr.dto.EmployeeBenefitDTO;
import com.example.hr.service.EmployeeBenefitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeBenefitServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeBenefitService employeeBenefitService;

    private EmployeeBenefitDTO benefit1;
    private EmployeeBenefitDTO benefit2;

    @BeforeEach
    void setUp() {
        benefit1 = new EmployeeBenefitDTO();
        benefit1.setEmployeeBenefitId(1);
        benefit1.setType("Healthcare");
        benefit1.setStatus("Active");
        benefit1.setDetails("Covers dental and vision");
        benefit1.setUserId(100);

        benefit2 = new EmployeeBenefitDTO();
        benefit2.setEmployeeBenefitId(2);
        benefit2.setType("Gym");
        benefit2.setStatus("Inactive");
        benefit2.setDetails("Expired 2023");
        benefit2.setUserId(100);
    }

    @Test
    void testGetEmployeeBenefitsForUser_withData() {
        Integer userId = 100;
        EmployeeBenefitDTO[] mockResponse = new EmployeeBenefitDTO[]{benefit1, benefit2};

        when(restTemplate.getForObject(
                "http://api-gateway:8080/finance/employee_benefit/user/{userId}",
                EmployeeBenefitDTO[].class, userId))
                .thenReturn(mockResponse);

        List<EmployeeBenefitDTO> result = employeeBenefitService.getEmployeeBenefitsForUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Healthcare", result.get(0).getType());
        assertEquals("Inactive", result.get(1).getStatus());
        assertEquals(100, result.get(0).getUserId());
    }

    @Test
    void testGetEmployeeBenefitsForUser_nullResponse() {
        Integer userId = 200;

        when(restTemplate.getForObject(
                "http://api-gateway:8080/finance/employee_benefit/user/{userId}",
                EmployeeBenefitDTO[].class, userId))
                .thenReturn(null);  // simulira praznu/null response

        List<EmployeeBenefitDTO> result = employeeBenefitService.getEmployeeBenefitsForUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
