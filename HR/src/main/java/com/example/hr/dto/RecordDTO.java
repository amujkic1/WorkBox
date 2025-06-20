package com.example.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
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
}
