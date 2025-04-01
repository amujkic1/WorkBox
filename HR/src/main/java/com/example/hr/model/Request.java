package com.example.hr.model;

import jakarta.persistence.*;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Request type cannot be blank")
    private String type;

    @NotBlank(message = "Text cannot be blank")
    private String text;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or present")
    private Date date;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false) // FK na tabelu Record
    private Record record;

    protected Request() {}

    public Request(String type, String text, Date date, String status, Record record) {
        this.type = type;
        this.text = text;
        this.date = date;
        this.status = status;
        this.record = record;
    }

    public Integer getId() {
        return id;
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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", record=" + (record != null ? record.getId() : "null") +
                '}';
    }
}
