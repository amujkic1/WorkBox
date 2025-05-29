package com.example.demo.repository;

import com.example.demo.models.CheckInRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CheckInRecordRepository extends JpaRepository<CheckInRecord,Integer> {

    List<CheckInRecord> findByCheckInDateBetween(Date startDate, Date endDate);
}
