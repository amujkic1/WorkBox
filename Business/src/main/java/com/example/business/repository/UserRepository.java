package com.example.business.repository;

import com.example.business.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    void deleteByUuid(UUID uuid);
    Optional<User> findByUuid(UUID uuid);
}
