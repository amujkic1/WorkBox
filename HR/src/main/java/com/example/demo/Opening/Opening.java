package com.example.demo.Opening;

import com.example.demo.Application.Application;
import com.example.demo.User.User;
import jakarta.persistence.*;
import java.util.ArrayList;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    @ManyToMany(mappedBy = "openings")
    private List<User> users = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
