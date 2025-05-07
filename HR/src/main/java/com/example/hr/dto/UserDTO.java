package com.example.hr.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String role;
    private String username;
    private String password;

    public UserDTO(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }
}
