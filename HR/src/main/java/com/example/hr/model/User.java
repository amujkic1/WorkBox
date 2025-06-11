package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NamedQuery(query = "select u from User u", name = "query_find_all_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid;

    @NotBlank(message = "First name cannot be blank")
    //@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    //@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    private String role;

    //@NotBlank(message = "Username cannot be blank")
    //@Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    //@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, digits, and underscores")
    private String username;

    //@NotBlank(message = "Password cannot be blank")
    //@Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    //@Column(unique = true)
    //@NotNull(message = "JMBG cannot be null")
    private Long jmbg;

    //@NotNull(message = "Birth date cannot be null")
    //@Past(message = "Birth date must be in the past")
    private Date birthDate;

    //@NotBlank(message = "Contact number cannot be blank")
    //@Pattern(regexp = "^\\+?[0-9]*$", message = "Contact number must be a valid number")
    private String contactNumber;

    //@NotBlank(message = "Address cannot be blank")
    private String address;

    //@NotBlank(message = "Email cannot be blank")
    //@Email(message = "Email must be valid")
    private String email;

    //@NotNull(message = "Employment date cannot be null")
    //@PastOrPresent(message = "Employment date must be in the past or present")
    private Date employmentDate;

    //@NotBlank(message = "Status cannot be blank")
    private String status;

    //@NotNull(message = "Working hours cannot be null")
    private Time workingHours;

    @ManyToMany
    @JoinTable(
            name = "user_openings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "opening_id")
    )
    @Builder.Default
    private List<Opening> openings = new ArrayList<>();

    public User(Integer id, String firstName, String lastName, String role,
                String username, String password, String email) {
        this.id = id;
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
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