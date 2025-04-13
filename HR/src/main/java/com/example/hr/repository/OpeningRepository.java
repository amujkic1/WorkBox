package com.example.hr.repository;

import com.example.hr.model.Opening;
import com.example.hr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpeningRepository extends JpaRepository<Opening, Integer> {
    List<Opening> findAllByUser(User user);
}
