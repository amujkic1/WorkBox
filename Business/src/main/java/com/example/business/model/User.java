package com.example.business.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "First name cannot be null")
    @Column(name="first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Column(name="last_name")
    private String lastName;

    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public void setUuid(UUID uuid){ this.uuid = uuid; }

    public User() { }

    public User(String firstName, String lastName, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.uuid = UUID.randomUUID();
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
