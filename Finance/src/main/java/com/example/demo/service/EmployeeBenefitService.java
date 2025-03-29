package com.example.demo.service;

import com.example.demo.models.EmployeeBenefit;
import com.example.demo.repository.EmployeeBenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeBenefitService {
    @Autowired
    private EmployeeBenefitRepository employeeBenefitRepository;

    public Optional<EmployeeBenefit> getEmployeeBenefitById(Integer id){
        return employeeBenefitRepository.findById(id);
    }

    public List<EmployeeBenefit> getAllEmployeeBenefit(){
        return employeeBenefitRepository.findAll();
    }

    public void insert(EmployeeBenefit employeeBenefit){
        employeeBenefitRepository.save(employeeBenefit);
    }

}
