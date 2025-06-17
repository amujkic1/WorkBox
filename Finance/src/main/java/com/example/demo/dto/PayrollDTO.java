package com.example.demo.dto;

import com.example.demo.models.EmployeeBenefit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PayrollDTO {
    private Integer id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String status;
    private Integer workingHours;
    private Integer totalWorkingHours;
    private Integer totalOvertimeHours = 0;
    private Double salary;
    private List<EmployeeBenefit> benefits;

    public PayrollDTO() {}

    public PayrollDTO(Integer id, UUID uuid, String firstName, String lastName, String status, Integer workingHours, Integer totalWorkingHours, Integer totalOvertimeHours, Double salary, List<EmployeeBenefit> benefits) {
        this.id = id;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.workingHours = workingHours;
        this.totalWorkingHours = totalWorkingHours;
        this.totalOvertimeHours = totalOvertimeHours;
        this.salary = salary;
        this.benefits = benefits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(Integer totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public Integer getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public void setTotalOvertimeHours(Integer totalOvertimeHours) {
        this.totalOvertimeHours = totalOvertimeHours;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<EmployeeBenefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<EmployeeBenefit> benefits) {
        this.benefits = benefits;
    }
}
