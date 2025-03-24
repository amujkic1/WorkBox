package com.example.demo.Opening;

import com.example.demo.Application.Application;
import com.example.demo.User.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Opening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openingName;
    private String description;
    private String conditions;
    private String benefits;
    private Date startDate;
    private Date endDate;
    private String result;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // FK na tabelu User
    private User user;

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    public Opening() {}

    public Opening(String openingName, String description, String conditions, String benefits,
                   Date startDate, Date endDate, String result, User user) {
        this.openingName = openingName;
        this.description = description;
        this.conditions = conditions;
        this.benefits = benefits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.result = result;
        this.user = user;
    }

    public Opening(String openingName, String description, User user) {
        this.openingName = openingName;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getOpeningName() {
        return openingName;
    }

    public void setOpeningName(String openingName) {
        this.openingName = openingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
