package com.example.demo.EmployeeBenefits;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeBenefits, Integer> {
}
