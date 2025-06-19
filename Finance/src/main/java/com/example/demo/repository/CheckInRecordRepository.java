package com.example.demo.repository;

import com.example.demo.models.CheckInRecord;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRecordRepository extends JpaRepository<CheckInRecord,Integer> {

    List<CheckInRecord> findByCheckInDateBetween(Date startDate, Date endDate);
    Optional<CheckInRecord> findByUserAndCheckInDate(User user, Date checkInDate);
    Optional<CheckInRecord> findTopByUserAndCheckInDateOrderByCheckInTimeDesc(User user, Date checkInDate);


}
