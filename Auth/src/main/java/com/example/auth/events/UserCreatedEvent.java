package com.example.auth.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserCreatedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String role;

    public UserCreatedEvent() {}

    @JsonCreator
    public UserCreatedEvent(
            @JsonProperty("uuid") UUID uuid,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("role") String role) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
