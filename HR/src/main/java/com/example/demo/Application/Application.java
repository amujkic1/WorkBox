package com.example.demo.Application;

import com.example.demo.Opening.Opening;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String documentationLink;
    private String status;
    private Double points;

    @ManyToOne
    @JoinColumn(name = "opening_id", nullable = false)
    private Opening opening;

    public Application() {}

    public Application(Date date, String firstName, String lastName, String email, String contactNumber, String documentationLink, String status, Double points, Opening opening) {
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

    public Long getId() {
        return id;
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

    public Opening getOpening() {
        return opening;
    }

    public void setOpening(Opening opening) {
        this.opening = opening;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", date=" + date +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", documentationLink='" + documentationLink + '\'' +
                ", status='" + status + '\'' +
                ", points=" + points +
                ", opening=" + (opening != null ? opening.getId() : "null") +
                '}';
    }
}
