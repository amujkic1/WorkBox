package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Time;
import java.util.Date;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotNull(message = "JMBG cannot be null")
    private Integer JMBG;

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
    private Time workingHours;

    public Record(Integer JMBG, Date birthDate, String contactNumber, String address,
                  String email, Date employmentDate, String status, Time workingHours) {
        this.JMBG = JMBG;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.address = address;
        this.email = email;
        this.employmentDate = employmentDate;
        this.status = status;
        this.workingHours = workingHours;
    }

    public Record() {
    }

    public Integer getJMBG() {
        return JMBG;
    }

    public void setJMBG(Integer JMBG) {
        this.JMBG = JMBG;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Time workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public String toString() {
        return "Record{" +
                "JMBG=" + JMBG +
                ", birthDate=" + birthDate +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", employmentDate=" + employmentDate +
                ", status='" + status + '\'' +
                ", workingHours=" + workingHours +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}