package com.example.demo.EmployeeBenefits;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class EmployeeService {
    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeBenefits insert(EmployeeBenefits employeeBenefits) {
        entityManager.persist(employeeBenefits);
        return employeeBenefits;
    }

    public EmployeeBenefits findById(Integer id) {
        return entityManager.find(EmployeeBenefits.class, id);
    }

    public List<EmployeeBenefits> findAll() {
        TypedQuery<EmployeeBenefits> query = entityManager.createQuery("SELECT o FROM employee_benefits o", EmployeeBenefits.class);
        return query.getResultList();
    }

    public EmployeeBenefits update(EmployeeBenefits employeeBenefits) {
        return entityManager.merge(employeeBenefits);
    }

    public void delete(int id) {
        EmployeeBenefits employeeBenefits = findById(id);
        if (employeeBenefits != null) {
            entityManager.remove(employeeBenefits);
        }
    }
}
