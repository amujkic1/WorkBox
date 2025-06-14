package com.example.demo.dto;

import com.example.demo.models.EmployeeBenefit;

import java.util.List;

public class EmployeeStatus {
    private Integer id;
    private String firstName;
    private String lastName;
    private String status;
    private String position;
    private Integer numberOfWorkingHours;
    private List<EmployeeBenefit> employeeBenefits;

    public EmployeeStatus(Integer id, String firstName, String lastName, String status, String position, Integer numberOfWorkingHours, List<EmployeeBenefit> employeeBenefits) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
}
