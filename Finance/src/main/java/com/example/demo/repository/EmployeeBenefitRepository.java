package com.example.demo.repository;

import com.example.demo.models.EmployeeBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit,Integer> {
    List<EmployeeBenefit> findByUser_UserId(Integer userId);
}
