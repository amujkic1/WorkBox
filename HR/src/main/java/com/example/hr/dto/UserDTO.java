package com.example.hr.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String role;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UUID uuid;

    public UserDTO(String role, String username, String password, String firstName, String lastName, String email, UUID uuid) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uuid = uuid;
    }
}
