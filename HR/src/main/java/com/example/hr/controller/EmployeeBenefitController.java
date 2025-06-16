package com.example.hr.controller;

import com.example.hr.dto.EmployeeBenefitDTO;
import com.example.hr.service.EmployeeBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class EmployeeBenefitController {

    @Autowired
    private EmployeeBenefitService employeeBenefitService;

    @GetMapping("/employee_benefit/user/{userId}")
    public List<EmployeeBenefitDTO> getUserBenefits(@PathVariable Integer userId) {
        return employeeBenefitService.getEmployeeBenefitsForUser(userId);
    }

}
