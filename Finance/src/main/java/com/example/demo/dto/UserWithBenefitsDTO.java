package com.example.demo.dto;

import com.example.demo.models.EmployeeBenefit;

import java.util.List;

public class UserWithBenefitsDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private List<EmployeeBenefit> benefits;

    public UserWithBenefitsDTO(Integer userId, String firstName, String lastName, List<EmployeeBenefit> benefits) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.benefits = benefits;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<EmployeeBenefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<EmployeeBenefit> benefits) {
        this.benefits = benefits;
    }
}
