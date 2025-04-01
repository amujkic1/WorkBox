package com.example.hr.dto;

import java.util.Date;

public class OpeningDTO {

    private Integer id;
    private String openingName;
    private String description;
    private String conditions;
    private String benefits;
    private Date startDate;
    private Date endDate;
    private String result;
    private Integer userId;

    public OpeningDTO(Integer id, String openingName, String description, String conditions, String benefits,
                      Date startDate, Date endDate, String result, Integer userId) {
        this.id = id;
        this.openingName = openingName;
        this.description = description;
        this.conditions = conditions;
        this.benefits = benefits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.result = result;
        this.userId = userId;
    }

    public OpeningDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpeningName() {
        return openingName;
    }

    public void setOpeningName(String openingName) {
        this.openingName = openingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}