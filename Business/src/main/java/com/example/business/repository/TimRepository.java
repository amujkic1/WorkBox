package com.example.business.repository;

import com.example.business.model.Tim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimRepository extends JpaRepository<Tim, Integer> {
}
