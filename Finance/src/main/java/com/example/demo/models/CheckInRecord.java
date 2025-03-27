package com.example.demo.models;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "check_in_record")
public class CheckInRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkInRecordId;

    @Column(name = "check_in_date")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Column(name = "check_in_time")
    @Temporal(TemporalType.TIME)
    private Time checkInTime;

    @Column(name = "check_out_time")
    @Temporal(TemporalType.TIME)
    private Time checkOutTime;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public CheckInRecord() {
    }

    public CheckInRecord(Date checkInDate, Time checkInTime, Time checkOutTime, User user) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.user = user;
    }

    public Integer getCheckInRecordId() {
        return checkInRecordId;
    }

    public void setCheckInRecordId(Integer checkInRecordId) {
        this.checkInRecordId = checkInRecordId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
