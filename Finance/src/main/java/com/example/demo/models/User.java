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

    @NotNull(message = "Hourly rate cannot be null)")
    private Double hourlyRate = 0.0; //Rename u salaryCoefficient

    @Column(name="user_UUID")
    private UUID userUUID;


    public User() {}

    public User(String firstName, String lastName, Double hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourlyRate = hourlyRate;
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

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }
}
