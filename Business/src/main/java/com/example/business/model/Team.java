package com.example.business.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="team_leader")
    private String teamLeader;

    public Team() { }

    public Team(String name, String teamLeader) {
        this.name = name;
        this.teamLeader = teamLeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
}
