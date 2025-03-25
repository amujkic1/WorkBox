package com.example.business.repository;

import com.example.business.model.Projekat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjekatRepository extends JpaRepository<Projekat,Integer> {
}
