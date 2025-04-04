package com.example.hr.dto;

import java.sql.Time;
import java.util.Date;

public class RecordDTO {
    private Integer id;
    private Date employmentDate;
    private String status;
    private Time workingHours;

    public RecordDTO() {
    }

    public RecordDTO(Integer id, Date employmentDate, String status, Time workingHours) {
        this.id = id;
        this.employmentDate = employmentDate;
        this.status = status;
        this.workingHours = workingHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Time workingHours) {
        this.workingHours = workingHours;
    }
}