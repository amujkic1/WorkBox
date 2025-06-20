package com.example.demo.dto;

import java.util.UUID;

public class WorkingHours {
    private Integer userId;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private Integer totalWorkingHours;
    private Integer totalOvertimeHours = 0;


    //private Map<Date, Integer> dailyWorkMap = new HashMap<>();


    public WorkingHours(Integer userId, UUID uuid, String firstName, String lastName, Integer totalWorkingHours) {
        this.userId = userId;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalWorkingHours = totalWorkingHours;
    }
/*
    public WorkingHours(Integer userId, String firstName, String lastName, Integer totalWorkingHours) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalWorkingHours = totalWorkingHours;

    }

 */


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(Integer totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public Integer getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public void setTotalOvertimeHours(Integer overtimeHours) {
        this.totalOvertimeHours = overtimeHours;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
