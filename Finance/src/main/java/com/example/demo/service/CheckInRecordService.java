package com.example.demo.service;

import com.example.demo.models.CheckInRecord;
import com.example.demo.repository.CheckInRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInRecordService {

    @Autowired
    private CheckInRecordRepository checkInRecordRepository;

    public Optional<CheckInRecord> getCheckInRecordById(Integer id){
        return checkInRecordRepository.findById(id);
    }

    public List<CheckInRecord> getAllCheckInRecords(){
        return checkInRecordRepository.findAll();
    }

    public void insert(CheckInRecord checkInRecord){
        checkInRecordRepository.save(checkInRecord);
    }


    public CheckInRecord updateCheckOutTime(CheckInRecord updatedRecord) {
        return checkInRecordRepository.findById(updatedRecord.getCheckInRecordId())
                .map(existingRecord -> {
                    existingRecord.setCheckOutTime(updatedRecord.getCheckOutTime());
                    return checkInRecordRepository.save(existingRecord);
                })
                .orElseThrow(() -> new RuntimeException("Check-in record not found with id: " + updatedRecord.getCheckInRecordId()));
    }


}
