package com.example.demo.User;

import com.example.demo.Record.Record;
import jakarta.persistence.*;

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

    // ManyToOne veza sa Record entitetom
    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false) // FK na Record tabelu
    private Record record;

    public User() { }

    public User(String firstName, String lastName, String role, String username, String password, Record record) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.record = record;
    }

    public User(String firstName, String lastName, String role, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
