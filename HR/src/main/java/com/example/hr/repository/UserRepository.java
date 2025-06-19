package com.example.hr.repository;

import com.example.hr.model.Record;
import com.example.hr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUuid(UUID uuid);
    //List<User> findAllByRecord(Record record);

    Optional<User> findByUuid(UUID uuid);
}