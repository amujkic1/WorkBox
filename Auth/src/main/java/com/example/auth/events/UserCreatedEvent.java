package com.example.auth.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public class UserCreatedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;

    // Konstruktor bez parametara, Jackson ga koristi
    public UserCreatedEvent() {}

    // Konstruktor koji Jackson koristi za deseralizaciju
    @JsonCreator
    public UserCreatedEvent(
            @JsonProperty("uuid") UUID uuid,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("email") String email) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Dodatni konstruktor sa UUID parametrom, za jednostavne slučajeve
    public UserCreatedEvent(UUID uuid) {
        this.uuid = uuid;
    }

    // Getters i Setters
    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
