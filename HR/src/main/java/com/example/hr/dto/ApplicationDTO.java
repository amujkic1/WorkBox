package com.example.hr.dto;

import java.util.Date;

public class ApplicationDTO {
    private Integer id;
    private Date date;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String documentationLink;
    private String status;
    private Double points;
    private Integer openingId;

    public ApplicationDTO(Date date, String firstName, String lastName, String email,
                          String contactNumber, String documentationLink, String status,
                          Double points, Integer openingId) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.documentationLink = documentationLink;
        this.status = status;
        this.points = points;
        this.openingId = openingId;
    }

    public ApplicationDTO(Integer id, Date date, String firstName, String lastName, String email, String contactNumber, String documentationLink, String status, Double points, Integer openingId) {
        this.id = id;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.documentationLink = documentationLink;
        this.status = status;
        this.points = points;
        this.openingId = openingId;
    }

    public ApplicationDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDocumentationLink() {
        return documentationLink;
    }

    public void setDocumentationLink(String documentationLink) {
        this.documentationLink = documentationLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Integer getOpeningId() {
        return openingId;
    }

    public void setOpeningId(Integer openingId) {
        this.openingId = openingId;
    }
}