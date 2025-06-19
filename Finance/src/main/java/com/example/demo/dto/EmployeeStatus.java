package com.example.demo.dto;

import com.example.demo.models.EmployeeBenefit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EmployeeStatus {
    private Integer id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String status;
    private Date employmentDate;
    private Integer numberOfWorkingHours;
    private List<EmployeeBenefit> employeeBenefits;

    public EmployeeStatus() {}

    public EmployeeStatus(Integer id, UUID uuid, String firstName, String lastName, String status, Date employmentDate, Integer numberOfWorkingHours, List<EmployeeBenefit> employeeBenefits) {
        this.id = id;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.employmentDate = employmentDate;
        this.numberOfWorkingHours = numberOfWorkingHours;
        this.employeeBenefits = employeeBenefits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNumberOfWorkingHours() {
        return numberOfWorkingHours;
    }

    public void setNumberOfWorkingHours(Integer numberOfWorkingHours) {
        this.numberOfWorkingHours = numberOfWorkingHours;
    }

    public List<EmployeeBenefit> getEmployeeBenefits() {
        return employeeBenefits;
    }

    public void setEmployeeBenefits(List<EmployeeBenefit> employeeBenefits) {
        this.employeeBenefits = employeeBenefits;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
