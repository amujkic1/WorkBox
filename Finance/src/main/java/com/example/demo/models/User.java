package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Size(min = 2, max = 50, message = "Name must have between 2 i 50 characters")
    @Column(name="first_name")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must have between 2 i 50 characters")
    @Column(name="last_name")
    private String lastName;

    @NotNull(message = "Salary coefficient cannot be null)")
    private Double salaryCoefficient = 0.0;

    @Column(name="user_UUID")
    private UUID userUUID;


    public User() {}

    public User(String firstName, String lastName, Double salaryCoefficient) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salaryCoefficient = salaryCoefficient;
        this.userUUID = UUID.randomUUID(); //Kroz Postman upit se automatski ne generise
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

    public Double getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(Double salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }
}
