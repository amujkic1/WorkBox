package com.example.demo.Request;

import com.example.demo.Record.Record;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String text;
    private Date date;
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

    public Long getId() {
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
