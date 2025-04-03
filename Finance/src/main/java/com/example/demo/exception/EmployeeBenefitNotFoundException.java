package com.example.demo.exception;

public class EmployeeBenefitNotFoundException extends RuntimeException {
    public EmployeeBenefitNotFoundException(Integer id) {
        super("Could not find employee benefit "+id);
    }
}
