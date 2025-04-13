package com.example.hr.repository;

import com.example.hr.model.Record;
import com.example.hr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    //List<User> findAllByRecord(Record record);
}