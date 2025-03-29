package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_benefit")
public class EmployeeBenefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer EmployeeBenefitId;
    private String type;
    private String status;
    private String details;
    //@Column(name = "salary_coefficient")
    private Double salaryCoefficient;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public EmployeeBenefit() {
    }

    public EmployeeBenefit(String type, String status, String details, Double salaryCoefficient, User user) {
        this.type = type;
        this.status = status;
        this.details = details;
        this.salaryCoefficient = salaryCoefficient;
        this.user = user;
    }

    public Integer getEmployeeBenefitId() {
        return EmployeeBenefitId;
    }

    public void setEmployeeBenefitId(Integer employeeBenefitId) {
        EmployeeBenefitId = employeeBenefitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(Double salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
