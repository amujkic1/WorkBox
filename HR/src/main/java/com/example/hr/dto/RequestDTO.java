package com.example.hr.dto;

import java.util.Date;

public class RequestDTO {
    public Integer id;
    private String type;
    private String text;
    private Date date;
    private String status;
    private Integer userId;

    public RequestDTO(){}

    public RequestDTO(Integer id, String type, String text, Date date, String status, Integer userId) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.date = date;
        this.status = status;
        this.userId = userId;
    }

    public RequestDTO(String type, String text, Date date, String status, Integer userId) {
        this.type = type;
        this.text = text;
        this.date = date;
        this.status = status;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setRecordId(Integer userId) {
        this.userId = userId;
    }
}