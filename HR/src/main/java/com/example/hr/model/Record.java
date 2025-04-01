package com.example.hr.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer JMBG;
    private Date birthDate;
    private String contactNumber;
    private String address;
    private String email;
    private Date employmentDate;
    private String status;
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