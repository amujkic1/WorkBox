package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {
    private Integer id;
    private Date employmentDate;
    private String status;
    private Integer workingHours;
    private UUID userUuid;

    public Integer getId() {
        return id;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public String getStatus() {
        return status;
    }

    public UUID getUserUuid() {
        return userUuid;
    }
}
