package com.example.demo.User;

import com.example.demo.Opening.Opening;
import com.example.demo.Record.Record;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQuery(query = "select u from User u", name = "query_find_all_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(name = "user_role")
    private String role;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToMany
    @JoinTable(
            name = "user_openings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "opening_id")
    )
    private List<Opening> openings = new ArrayList<>();

    public User() { }

    public User(String firstName, String lastName, String role, String username, String password, Record record) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.record = record;
    }

    public Long getId() {
        return id;
    }

    public List<Opening> getOpenings() {
        return openings;
    }

    public void setOpenings(List<Opening> openings) {
        this.openings = openings;
    }

    public void addOpening(Opening opening) {
        this.openings.add(opening);
        opening.getUsers().add(this);
    }

    public void removeOpening(Opening opening) {
        this.openings.remove(opening);
        opening.getUsers().remove(this);
    }
}
