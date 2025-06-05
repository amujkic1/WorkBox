package com.example.hr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Opening name cannot be blank")
    @Size(min = 2, max = 100, message = "Opening name must be between 2 and 100 characters")
    private String openingName;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Conditions cannot be blank")
    private String conditions;

    @NotBlank(message = "Benefits cannot be blank")
    private String benefits;

    @NotNull(message = "Start date cannot be null")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the future or present")
    private Date endDate;

    @Size(min = 2, max = 50, message = "Result must be between 2 and 50 characters")
    private String result;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "opening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany(mappedBy = "openings")
    private List<User> users = new ArrayList<>();

    public Opening(String openingName, String description, String conditions, String benefits, Date startDate,
                   Date endDate, String result, User user, List<Application> applications, List<User> users) {
        this.openingName = openingName;
        this.description = description;
        this.conditions = conditions;
        this.benefits = benefits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.result = result;
        this.user = user;
        this.applications = applications;
        this.users = users;
    }
}