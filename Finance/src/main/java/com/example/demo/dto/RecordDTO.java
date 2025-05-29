package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {
    private Integer id;
    private Date employmentDate;
    private String status;
    private Integer workingHours;

    public Integer getId() {
        return id;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

}
