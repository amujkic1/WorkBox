package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "JMBG cannot be null")
    private Long jmbg;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private Date birthDate;

    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Contact number must be a valid number")
    private String contactNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Employment date cannot be null")
    @PastOrPresent(message = "Employment date must be in the past or present")
    private Date employmentDate;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Working hours cannot be null")
    private Integer workingHours;
}