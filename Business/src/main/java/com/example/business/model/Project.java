package com.example.business.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Column(name="takeover_date")
    @Temporal(TemporalType.DATE)
    private Date takeoverDate;

    @Column(name="start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name="end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name="project_manager")
    private String projectManager;

    @Column(name="client_contact")
    private String clientContact;

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Project(){};

    public Project(String title, Date publicationDate, Date takeoverDate, Date startDate, Date endDate, String projectManager, String clientContact, Team team) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.takeoverDate = takeoverDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectManager = projectManager;
        this.clientContact = clientContact;
        this.team = team;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getTakeoverDate() {
        return takeoverDate;
    }

    public void setTakeoverDate(Date takeoverDate) {
        this.takeoverDate = takeoverDate;
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

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}








