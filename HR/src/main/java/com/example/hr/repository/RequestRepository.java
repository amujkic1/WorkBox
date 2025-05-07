package com.example.hr.repository;
import com.example.hr.model.Request;
import com.example.hr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByUser(User user);
}
