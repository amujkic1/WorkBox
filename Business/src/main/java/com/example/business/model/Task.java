package com.example.business.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name="end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name="submission_date")
    @Temporal(TemporalType.DATE)
    private Date submissionDate;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() { }

    public Task(Date startDate, Date endDate, Date submissionDate, String name, String description, String status, Project project, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.submissionDate = submissionDate;
        this.name = name;
        this.description = description;
        this.status = status;
        this.project = project;
        this.user = user;
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

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
