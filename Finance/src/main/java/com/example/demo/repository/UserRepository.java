package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserUUID(UUID userUUID);
    void deleteByUserUUID(UUID userUUID);
}
