package com.example.hr.service;

import com.example.hr.dto.EmployeeBenefitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeBenefitService {

    private final RestTemplate restTemplate;

    @Autowired
    public EmployeeBenefitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String financeServiceUrl = "http://finance-service/employee_benefit/{userId}";

    public List<EmployeeBenefitDTO> getEmployeeBenefitsForUser(Integer userId) {

        EmployeeBenefitDTO[] benefits = restTemplate.getForObject(financeServiceUrl, EmployeeBenefitDTO[].class, userId);
        return benefits != null ? Arrays.asList(benefits) : List.of();
    }
}
