package com.example.demo.repository;

import com.example.demo.models.EmployeeBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit,Integer> {
}
