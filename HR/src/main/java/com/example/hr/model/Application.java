package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Date cannot be null")
    private Date date;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Contact number can only contain digits and an optional '+' sign at the beginning")
    private String contactNumber;

    @URL(message = "Invalid documentation link format")
    private String documentationLink;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Points cannot be null")
    @Min(value = 0, message = "Points must be greater than or equal to 0")
    @Max(value = 100, message = "Points must be less than or equal to 100")
    private Double points;

    @ManyToOne
    @JoinColumn(name = "opening_id", nullable = false)
    private Opening opening;

    public Application(Date date, String firstName, String lastName, String email,
                       String contactNumber, String documentationLink, String status,
                       Double points, Opening opening) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.documentationLink = documentationLink;
        this.status = status;
        this.points = points;
        this.opening = opening;
    }

}