package com.example.hr.repository;

import com.example.hr.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    List<Record> findByStatus(String status);
}
