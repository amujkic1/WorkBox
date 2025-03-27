package com.example.demo.User;

import jakarta.persistence.*;

import java.rmi.server.UID;

@Entity
@Table(name = "users")
@NamedQuery(query = "select u from User u", name = "query_find_all_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    //@Column(name = "user_UID")
    //private UID userUID;

    // Relacije sa ostalim tabelama


    public User() {
    }

    public User(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.userUID = userUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    //public UID getUserUID() {
    //    return userUID;
    //}

    //public void setUserUID(UID userUID) {
    //    this.userUID = userUID;
    //}
}
