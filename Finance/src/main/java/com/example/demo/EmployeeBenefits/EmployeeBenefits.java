package com.example.demo.EmployeeBenefits;

import com.example.demo.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_benefits")
public class EmployeeBenefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String benefitType;
    private String benefitStatus;
    private String details;
    private Double wageCoefficient;
    //private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public EmployeeBenefits() {
    }

    public EmployeeBenefits(Integer id, String benefitType, String benefitStatus, String details, Double wageCoefficient, User user) {
        this.id = id;
        this.benefitType = benefitType;
        this.benefitStatus = benefitStatus;
        this.details = details;
        this.wageCoefficient = wageCoefficient;
        //this.userId = userId;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBenefitType() {
        return benefitType;
    }

    public void setBenefitType(String benefitType) {
        this.benefitType = benefitType;
    }

    public String getBenefitStatus() {
        return benefitStatus;
    }

    public void setBenefitStatus(String benefitStatus) {
        this.benefitStatus = benefitStatus;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getWageCoefficient() {
        return wageCoefficient;
    }

    public void setWageCoefficient(Double wageCoefficient) {
        this.wageCoefficient = wageCoefficient;
    }

    //public Integer getUserId() {
     //   return userId;
    //}

    //public void setUserId(Integer userId) {
    //    this.userId = userId;
   // }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
